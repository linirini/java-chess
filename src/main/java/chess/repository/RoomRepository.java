package chess.repository;

import chess.domain.room.Room;

import java.util.Optional;

public interface RoomRepository {
    long save(final Room room);

    Optional<Room> findByName(final String name);

    boolean isExistName(String name);
}
