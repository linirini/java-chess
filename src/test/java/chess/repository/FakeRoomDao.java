package chess.repository;

import chess.domain.Name;
import chess.domain.room.Room;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeRoomDao implements RoomRepository {

    private final Map<Long, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public long save(final Room room) {
        long id = rooms.size() + 1L;
        Name name = new Name(room.getName());
        rooms.put(id, new Room(id, name));
        return id;
    }

    @Override
    public Optional<Room> findByName(final String name) {
        return rooms.values().stream()
                .filter(value -> value.getName().equals(name))
                .findAny();
    }

    @Override
    public boolean isExistName(final String name) {
        return rooms.values().stream().anyMatch(room -> room.getName().equals(name));
    }
}
