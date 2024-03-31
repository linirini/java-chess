package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeBoardDao implements BoardRepository {
    private final Map<Long, FakeBoard> boards = new ConcurrentHashMap<>();

    @Override
    public Map<Position, Piece> findAllByRoomId(final long roomId) {
        List<FakeBoard> fakeBoards = boards.values().stream()
                .filter(value -> value.roomId == roomId)
                .toList();
        Map<Position, Piece> pieces = new HashMap<>();
        for (final FakeBoard fakeBoard : fakeBoards) {
            pieces.put(fakeBoard.position, fakeBoard.piece);
        }
        return pieces;
    }

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {
        long id = boards.size()+1L;
        boards.put(id,new FakeBoard(position,piece,roomId));
    }

    @Override
    public boolean isExistByRoomId(final long roomId) {
        return boards.values().stream().anyMatch(fakeBoard -> fakeBoard.roomId == roomId);
    }

    private record FakeBoard (Position position, Piece piece, long roomId){
    }
}
