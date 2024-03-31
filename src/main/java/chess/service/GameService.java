package chess.service;

import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.BoardRepository;
import chess.repository.PieceRepository;
import chess.repository.RoomRepository;

import java.util.Map;

public class GameService {
    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;
    private final PieceRepository pieceRepository;

    public GameService(final BoardRepository boardRepository, final RoomRepository roomRepository, final PieceRepository pieceRepository) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
        this.pieceRepository = pieceRepository;
    }

    public ChessGame findGame(final long roomId) {
        Map<Position, Piece> pieces = boardRepository.findAllByRoomId(roomId);
        Turn turn = roomRepository.findTurnById(roomId).orElseThrow();
        return new ChessGame(roomId, pieces, turn);
    }
}
