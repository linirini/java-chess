package chess.repository;

import chess.domain.game.Turn;
import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    long save(final Room room, final Turn turn);

    Optional<Room> findByName(final String name);

    boolean isExistName(String name);

    List<Room> findAll();

    Optional<Turn> findTurnById(long roomId);
}
