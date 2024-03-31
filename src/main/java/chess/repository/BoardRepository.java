package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;

public interface BoardRepository {
    Map<Position, Piece> findAllByRoomId(long roomId);

    void save(Position position, long pieceId, long roomId);
}
