package chess.service;

import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Rook;
import chess.domain.position.Position;
import chess.domain.room.Room;
import chess.repository.*;
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
        room = new Room(name);
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
                () -> assertThat(game.turn()).isEqualTo(expectedTurn.getTurn()),
                () -> assertThat(game.getBoardStatus().pieceInfos()).hasSize(pieces.size())
        );
    }

    @DisplayName("진행 중이던 게임이 없으면 게임을 새로 생성한다.")
    @Test
    void createGame() {
        //given
        long newRoomId = roomRepository.save(new Room("찰리방"), Turn.first());
        int initialPieceCount = 32;

        //when
        ChessGame game = gameService.findGame(newRoomId);

        //then
        assertAll(
                () -> assertThat(game.turn()).isEqualTo(Turn.first().getTurn()),
                () -> assertThat(game.getBoardStatus().pieceInfos()).hasSize(initialPieceCount)
        );
    }
}
