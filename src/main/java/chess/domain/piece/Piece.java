package chess.domain.piece;

import chess.domain.position.Position;

public abstract class Piece {
    protected final PieceColor color;

    public Piece(final PieceColor color) {
        this.color = color;
    }

    public abstract boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation);

    public boolean isKing() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isColor(final PieceColor color) {
        return this.color == color;
    }

    public PieceColor color() {
        return color;
    }
}
