package chess.repository;

import chess.db.DBConnection;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class BoardDao implements BoardRepository {

    private final Connection connection;

    public BoardDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId) {
        return null;
    }

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {
        String query = "INSERT INTO board (position, piece_id, room_id) VALUES (?,(SELECT piece_id FROM piece WHERE type = ? AND color = ?),?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, convertPosition(position));
            preparedStatement.setString(2, piece.type().name());
            preparedStatement.setString(3, piece.color().name());
            preparedStatement.setLong(4, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(final Position position, final Long pieceId, final long roomId) {
        String query = "INSERT INTO board (position, piece_id, room_id) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, convertPosition(position));
            preparedStatement.setLong(2, pieceId);
            preparedStatement.setLong(3, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByRoomId(final long roomId) {
        final String query = "SELECT * FROM board WHERE room_id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByRoomIdAndPosition(final long roomId, final Position position) {
        final String query = "SELECT * FROM board WHERE room_id = ? AND position = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Long> findPieceIdByRoomIdAndPosition(final long roomId, final Position position) {
        final String query = "SELECT piece_id FROM board WHERE room_id = ? AND position = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                return Optional.of(resultset.getLong("piece_id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByRoomIdAndPosition(final long roomId, final Position position) {
        final String query = "DELETE FROM board WHERE room_id = ? AND position = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertPosition(Position position) {
        return position.valueOfFile() + position.valueOfRank();
    }
}
