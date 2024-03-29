package chess.domain.piece;

import chess.domain.position.PieceRelation;
import chess.domain.position.Position;

public abstract class Piece {
    protected final PieceColor color;
    protected final PieceType type;

    public Piece(final PieceColor color, final PieceType type) {
        this.color = color;
        this.type = type;
    }

    public abstract boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation);

    public boolean isColor(final PieceColor color) {
        return this.color == color;
    }

    public PieceColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public double score() {
        return type.score();
    }

    public boolean isPawn() {
        return type == PieceType.PAWN;
    }
}
