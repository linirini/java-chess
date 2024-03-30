package chess.repository;

import chess.domain.room.Room;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class FakeRoomDao implements RoomRepository {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public long save(final Room room) {
        rooms.put(room.getName(), room);
        return room.getId();
    }

    @Override
    public long findIdByName(final String name) {
        final Room room = rooms.get(name);
        if (room == null) {
            throw new NoSuchElementException();
        }

        return room.getId();
    }

    @Override
    public boolean isExistName(final String name) {
        return rooms.containsKey(name);
    }
}
