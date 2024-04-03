package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeBoardDao implements BoardRepository {
    private final Map<Long, FakeBoard> boards = new ConcurrentHashMap<>();

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {
        long id = boards.size() + 1L;
        boards.put(id, new FakeBoard(position, piece, roomId));
    }

    @Override
    public boolean existsByRoomId(final long roomId) {
        return boards.values().stream().anyMatch(fakeBoard -> fakeBoard.roomId == roomId);
    }

    @Override
    public boolean existsByRoomIdAndPosition(final long roomId, final Position position) {
        return boards.values().stream()
                .anyMatch(value -> value.roomId == roomId && value.position.equals(position));
    }

    @Override
    public Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId) {
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
    public Optional<Piece> findPieceByRoomIdAndPosition(final long roomId, final Position position) {
        return boards.values().stream()
                .filter(value -> value.roomId == roomId && value.position.equals(position))
                .map(FakeBoard::piece)
                .findAny();
    }

    @Override
    public void deleteByRoomIdAndPosition(final long roomId, final Position position) {
        long id = boards.entrySet().stream()
                .filter(entry -> entry.getValue().roomId == roomId && entry.getValue().position.equals(position))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없습니다."));
        boards.remove(id);
    }

    private record FakeBoard(Position position, Piece piece, long roomId) {
    }
}
