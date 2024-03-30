package chess.repository;

import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public class RoomDao implements RoomRepository {
    @Override
    public long save(final Room room) {
        return 0;
    }

    @Override
    public Optional<Room> findByName(final String name) {
        return Optional.empty();
    }

    @Override
    public boolean isExistName(final String name) {
        return false;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }
}
