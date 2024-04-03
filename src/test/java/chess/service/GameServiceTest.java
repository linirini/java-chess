package chess.service;

import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Rook;
import chess.domain.position.Position;
import chess.domain.room.Room;
import chess.repository.BoardRepository;
import chess.repository.FakeBoardDao;
import chess.repository.FakeRoomDao;
import chess.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GameServiceTest {
    GameService gameService;
    BoardRepository boardRepository;
    RoomRepository roomRepository;
    Room room;
    long roomId;
    Map<Position, Piece> pieces;
    Turn turn;

    @BeforeEach
    void init() {
        boardRepository = new FakeBoardDao();
        roomRepository = new FakeRoomDao();
        gameService = new GameService(boardRepository, roomRepository);

        String name = "리니방";
        room = Room.from(name);
        turn = new Turn(PieceColor.BLACK);
        roomId = roomRepository.save(room, turn);

        pieces = new HashMap<>();
        pieces.put(Position.of("b4"), new Rook(PieceColor.WHITE));
        pieces.put(Position.of("b7"), new Rook(PieceColor.BLACK));
        pieces.put(Position.of("c8"), new Knight(PieceColor.BLACK));
        savePieces(pieces);
    }

    private void savePieces(final Map<Position, Piece> pieces) {
        for (final Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            boardRepository.save(position, piece, roomId);
        }
    }

    @DisplayName("room id로 진행 중이던 ChessGame을 입장한다.")
    @Test
    void findGame() {
        //given
        Turn expectedTurn = roomRepository.findTurnById(roomId).get();

        //when
        ChessGame game = gameService.findGame(roomId);

        //then
        assertAll(
                () -> assertThat(game.turn().getTurn()).isEqualTo(expectedTurn.getTurn()),
                () -> assertThat(game.getBoardStatus().piecePositionInfos()).hasSize(pieces.size())
        );
    }

    @DisplayName("진행 중이던 게임이 없으면 게임을 새로 생성한다.")
    @Test
    void createGame() {
        //given
        long newRoomId = roomRepository.save(Room.from("찰리방"), Turn.first());
        int initialPieceCount = 32;

        //when
        ChessGame game = gameService.findGame(newRoomId);

        //then
        assertAll(
                () -> assertThat(game.turn().getTurn()).isEqualTo(Turn.first().getTurn()),
                () -> assertThat(game.getBoardStatus().piecePositionInfos()).hasSize(initialPieceCount)
        );
    }

    @DisplayName("기물이 움직인다")
    @Test
    void move() {
        //given
        ChessGame game = gameService.findGame(roomId);
        String source = "b7";
        String target = "b5";
        Turn expectedTurn = new Turn(PieceColor.WHITE);

        //when
        gameService.move(game, source, target);

        //then
        assertAll(
                () -> assertThat(boardRepository.findPieceByRoomIdAndPosition(roomId, Position.of(source))).isEmpty(),
                () -> assertThat(boardRepository.findPieceByRoomIdAndPosition(roomId, Position.of(target))).isPresent(),
                () -> assertThat(roomRepository.findTurnById(roomId).get().getTurn()).isEqualTo(expectedTurn.getTurn())
        );
    }
}
