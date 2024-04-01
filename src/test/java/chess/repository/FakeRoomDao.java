package chess.repository;

import chess.domain.Name;
import chess.domain.game.Turn;
import chess.domain.room.Room;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeRoomDao implements RoomRepository {

    private final Map<Long, Room> rooms = new ConcurrentHashMap<>();
    private final Map<Long, Turn> turns = new ConcurrentHashMap<>();

    @Override
    public long save(final Room room, final Turn turn) {
        long id = rooms.size() + 1L;
        Name name = new Name(room.getName());
        rooms.put(id, new Room(id, name));
        turns.put(id, turn);
        return id;
    }

    @Override
    public boolean existsByName(final String name) {
        return rooms.values().stream().anyMatch(room -> room.getName().equals(name));
    }

    @Override
    public List<Room> findAll() {
        return rooms.values().stream().toList();
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        return rooms.values().stream()
                .filter(value -> value.getName().equals(name))
                .map(Room::getId)
                .findAny();
    }

    @Override
    public Optional<Turn> findTurnById(final long roomId) {
        return turns.entrySet().stream()
                .filter(entry -> entry.getKey() == roomId)
                .map(Map.Entry::getValue)
                .findAny();
    }

    @Override
    public void updateTurnByRoomId(final long roomId, final Turn turn) {
        turns.replace(roomId, turn);
    }
}
