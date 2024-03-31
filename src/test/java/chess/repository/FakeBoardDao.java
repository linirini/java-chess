package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeBoardDao implements BoardRepository {
    private final Map<Long, FakeBoard> boards = new ConcurrentHashMap<>();

    @Override
    public Map<Position, Piece> findAllByRoomId(final long roomId) {
        return new HashMap<>();
    }

    @Override
    public void save(final Position position, final long pieceId, final long roomId) {
        long id = boards.size()+1L;
        boards.put(id,new FakeBoard(position,pieceId,roomId));
    }

    private record FakeBoard (Position position, long pieceId, long roomId){
    }
}
