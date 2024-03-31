package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.piece.type.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakePieceDao implements PieceRepository {

    private static final Map<Long, Piece> pieces = new ConcurrentHashMap<>();

    static{
        pieces.put(1L, new Bishop(PieceColor.WHITE));
        pieces.put(2L, new King(PieceColor.WHITE));
        pieces.put(3L, new Knight(PieceColor.WHITE));
        pieces.put(4L, new Queen(PieceColor.WHITE));
        pieces.put(5L, new Rook(PieceColor.WHITE));
        pieces.put(6L, new Pawn(PieceColor.WHITE));
        pieces.put(7L, new Bishop(PieceColor.BLACK));
        pieces.put(8L, new King(PieceColor.BLACK));
        pieces.put(9L, new Knight(PieceColor.BLACK));
        pieces.put(10L, new Queen(PieceColor.BLACK));
        pieces.put(11L, new Rook(PieceColor.BLACK));
        pieces.put(12L, new Pawn(PieceColor.WHITE));
    }

    @Override
    public long findIdByTypeAndColor(final String type, final String color) {
        PieceType pieceType = PieceType.valueOf(type);
        PieceColor pieceColor = PieceColor.valueOf(color);
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().type() == pieceType && entry.getValue().isColor(pieceColor))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
    }
}
