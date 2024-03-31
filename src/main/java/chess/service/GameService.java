package chess.service;

import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;

import java.util.Map;

public class GameService {
    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;

    public GameService(final BoardRepository boardRepository, final RoomRepository roomRepository) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
    }

    public ChessGame findGame(final long roomId) {
        Map<Position, Piece> pieces = boardRepository.findAllByRoomId(roomId);
        if(isBoardNotReady(roomId)){
            createBoard(roomId);
            return ChessGame.create(roomId, ChessBoardGenerator.getInstance());
        }
        Turn turn = roomRepository.findTurnById(roomId).orElseThrow();
        return ChessGame.enter(roomId, pieces, turn);
    }

    private void createBoard(final long roomId) {
        for (final Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            boardRepository.save(position, piece, roomId);
        }
    }

    private boolean isBoardNotReady(final long roomId) {
        return !boardRepository.isExistByRoomId(roomId);
    }
}
