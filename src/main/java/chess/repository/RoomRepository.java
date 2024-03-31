package chess.repository;

import chess.domain.game.Turn;
import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    long save(final Room room, final Turn turn);

    Optional<Long> findIdByName(final String name);

    boolean isExistName(final String name);

    List<Room> findAll();

    Optional<Turn> findTurnById(final long roomId);

    void updateTurnByRoomId(final long roomId, final Turn turn);
}
