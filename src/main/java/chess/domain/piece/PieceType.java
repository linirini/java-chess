package chess.domain.piece;

import chess.domain.position.ChessDirection;

import java.util.List;

public enum PieceType {
    PAWN(ChessDirection.combine(ChessDirection.upSide(), ChessDirection.downSide()), 1),
    ROOK(ChessDirection.cross(), 5),
    KNIGHT(ChessDirection.LShapedDirections(), 2.5),
    BISHOP(ChessDirection.diagonal(), 3),
    KING(ChessDirection.combine(ChessDirection.cross(), ChessDirection.diagonal()), 0),
    QUEEN(ChessDirection.combine(ChessDirection.cross(), ChessDirection.diagonal()), 9);

    private final List<ChessDirection> directions;
    private final double score;

    PieceType(final List<ChessDirection> directions, final double score) {
        this.directions = directions;
        this.score = score;
    }

    public boolean contains(ChessDirection direction) {
        return directions.contains(direction);
    }

    public double score() {
        return score;
    }

    public double halfScore() {
        return score / 2;
    }
}
