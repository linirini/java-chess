package chess.repository;

import chess.db.DBConnection;
import chess.domain.Name;
import chess.domain.game.Turn;
import chess.domain.piece.PieceColor;
import chess.domain.room.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDao implements RoomRepository {
    private static final String SAVE_FAILURE = "방 정보 저장에 실패하였습니다";

    private static Turn createTurn(final String turn) throws SQLException {
        return new Turn(PieceColor.valueOf(turn));
    }

    @Override
    public long save(final Room room, final Turn turn) {
        final String query = "INSERT INTO room (name, turn) VALUES(?, ?)";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, room.getName());
            preparedStatement.setString(2, turn.getTurn().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new RuntimeException(SAVE_FAILURE);
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(final String name) {
        final String query = "SELECT * FROM room WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAll() {
        final String query = "SELECT * FROM room";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(createRoom(resultSet));
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        final String query = "SELECT room_id FROM room WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("room_id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Turn> findTurnById(final long roomId) {
        final String query = "SELECT turn FROM room WHERE room_id = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createTurn(resultSet.getString("turn")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTurnByRoomId(final long roomId, final Turn turn) {
        final String query = "UPDATE room SET turn = ? WHERE room_id = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.getTurn().name());
            preparedStatement.setLong(2, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Room createRoom(final ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getLong("room_id"),
                new Name(resultSet.getString("name"))
        );
    }
}
