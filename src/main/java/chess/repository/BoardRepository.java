package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;
import java.util.Optional;

public interface BoardRepository {
    Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId);

    void save(final Position position, final Piece piece, final long roomId);

    void save(final Position position, final Long pieceId, final long roomId);

    boolean isExistByRoomId(final long roomId);

    boolean isExistByRoomIdAndPosition(final long roomId, final Position position);

    Optional<Long> findPieceIdByRoomIdAndPosition(final long roomId, final Position position);

    void deleteByRoomIdAndPosition(final long roomId, final Position position);
}
