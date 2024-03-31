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

class GameServiceTest {
    GameService gameService;
    BoardRepository boardRepository;
    RoomRepository roomRepository;
    PieceRepository pieceRepository;
    Room room;
    long roomId;
    Map<Position, Piece> pieces;
    Turn turn;

    @BeforeEach
    void init() {
        boardRepository = new FakeBoardDao();
        roomRepository = new FakeRoomDao();
        pieceRepository = new FakePieceDao();
        gameService = new GameService(boardRepository, roomRepository, pieceRepository);

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

    @DisplayName("room id로 ChessGame을 찾아온다.")
    @Test
    void findGame() {
        //given
        Turn expectedTurn = roomRepository.findTurnById(roomId).get();

        //when
        ChessGame game = gameService.findGame(roomId);

        //then
        assertThat(game.turn()).isEqualTo(expectedTurn.getTurn());
    }

    private void savePieces(final Map<Position, Piece> pieces) {
        for (final Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            String type = entry.getValue().type().name();
            String color = entry.getValue().color().name();
            long pieceId = pieceRepository.findIdByTypeAndColor(type, color);
            boardRepository.save(position, pieceId, roomId);
        }
    }
}
