package chess.repository;

public interface PieceRepository {
    long findIdByTypeAndColor(String type, final String color);
}
