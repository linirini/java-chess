package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;
import java.util.Optional;

public class BoardDao implements BoardRepository {
    @Override
    public Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId) {
        return null;
    }

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {

    }

    @Override
    public void save(final Position position, final Long pieceId, final long roomId) {

    }

    @Override
    public boolean existsByRoomId(final long roomId) {
        return false;
    }

    @Override
    public boolean existsByRoomIdAndPosition(final long roomId, final Position position) {
        return false;
    }

    @Override
    public Optional<Long> findPieceIdByRoomIdAndPosition(final long roomId, final Position position) {
        return Optional.empty();
    }

    @Override
    public void deleteByRoomIdAndPosition(final long roomId, final Position position) {

    }
}
