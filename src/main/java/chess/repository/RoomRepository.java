package chess.repository;

import chess.domain.room.Room;

public interface RoomRepository {
    long save(final Room room);

    long findIdByName(final String name);
}
