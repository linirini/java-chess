package chess.domain.piece;

import chess.domain.piece.type.*;

import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    BISHOP(Bishop.class, Bishop::new),
    KING(King.class, King::new),
    KNIGHT(Knight.class, Knight::new),
    PAWN(Pawn.class, Pawn::new),
    QUEEN(Queen.class, Queen::new),
    ROOK(Rook.class, Rook::new);

    private final Class<? extends Piece> pieceClass;
    private final Function<PieceColor, Piece> function;

    PieceType(final Class<? extends Piece> pieceClass, final Function<PieceColor, Piece> function) {
        this.pieceClass = pieceClass;
        this.function = function;
    }

    public static PieceType findType(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.pieceClass.isInstance(piece))
                .findFirst()
                .orElseThrow();
    }

    public Piece getPiece(PieceColor color) {
        return this.function.apply(color);
    }
}
