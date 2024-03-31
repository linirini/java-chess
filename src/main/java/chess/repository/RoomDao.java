package chess.repository;

import chess.domain.game.Turn;
import chess.domain.room.Room;

import java.util.List;
import java.util.Optional;

public class RoomDao implements RoomRepository {
    @Override
    public long save(final Room room, final Turn turn) {
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

    @Override
    public Optional<Turn> findTurnById(final long roomId) {
        return Optional.empty();
    }

    @Override
    public void updateTurnByRoomId(final long roomId, final Turn turn) {

    }
}
