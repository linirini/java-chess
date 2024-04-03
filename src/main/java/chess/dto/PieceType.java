package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.*;

import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    WHITE_BISHOP(Bishop.class, PieceColor.WHITE, Bishop::new),
    BLACK_BISHOP(Bishop.class, PieceColor.BLACK, Bishop::new),
    WHITE_KING(King.class, PieceColor.WHITE, King::new),
    BLACK_KING(King.class, PieceColor.BLACK, King::new),
    WHITE_KNIGHT(Knight.class, PieceColor.WHITE, Knight::new),
    BLACK_KNIGHT(Knight.class, PieceColor.BLACK, Knight::new),
    WHITE_PAWN(Pawn.class, PieceColor.WHITE, Pawn::new),
    BLACK_PAWN(Pawn.class, PieceColor.BLACK, Pawn::new),
    WHITE_QUEEN(Queen.class, PieceColor.WHITE, Queen::new),
    BLACK_QUEEN(Queen.class, PieceColor.BLACK, Queen::new),
    WHITE_ROOK(Rook.class, PieceColor.WHITE, Rook::new),
    BLACK_ROOK(Rook.class, PieceColor.BLACK, Rook::new);

    private final Class<? extends Piece> pieceClass;
    private final PieceColor pieceColor;
    private final Function<PieceColor, Piece> function;

    PieceType(final Class<? extends Piece> pieceClass, final PieceColor pieceColor, final Function<PieceColor, Piece> function) {
        this.pieceClass = pieceClass;
        this.pieceColor = pieceColor;
        this.function = function;
    }

    public static PieceType findType(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.pieceClass.isInstance(piece) && piece.isColor(pieceType.pieceColor))
                .findFirst()
                .orElseThrow();
    }

    public Piece getPiece() {
        return this.function.apply(this.pieceColor);
    }
}
