package chess.repository;

import chess.db.DBConnection;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardDao implements BoardRepository {

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {
        String query = "INSERT INTO board (position, piece_type, room_id) VALUES (?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, convertPosition(position));
            preparedStatement.setString(2, PieceType.findType(piece).name());
            preparedStatement.setLong(3, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByRoomId(final long roomId) {
        final String query = "SELECT * FROM board WHERE room_id = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId) {
        final String query = "SELECT position, piece_type FROM board AS p WHERE room_id = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Position, Piece> pieces = new HashMap<>();
            while (resultSet.next()) {
                pieces.put(createPosition(resultSet), createPiece(resultSet));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Piece> findPieceByRoomIdAndPosition(final long roomId, final Position position) {
        final String query = "SELECT piece_type FROM board WHERE room_id = ? AND position = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                return Optional.of(createPiece(resultset));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByRoomIdAndPosition(final long roomId, final Position position) {
        final String query = "DELETE FROM board WHERE room_id = ? AND position = ?";
        try (Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, convertPosition(position));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Position createPosition(final ResultSet resultSet) throws SQLException {
        return Position.of(resultSet.getString("position"));
    }

    private Piece createPiece(final ResultSet resultSet) throws SQLException {
        PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));

        return pieceType.getPiece();
    }

    private String convertPosition(Position position) {
        return position.valueOfFile() + position.valueOfRank();
    }
}
