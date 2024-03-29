package chess.domain.piece;

import chess.domain.position.ChessDirection;

import java.util.List;

public enum PieceType {
    PAWN(ChessDirection.combine(ChessDirection.upSide(), ChessDirection.downSide())),
    ROOK(ChessDirection.cross()),
    KNIGHT(ChessDirection.LShapedDirections()),
    BISHOP(ChessDirection.diagonal()),
    KING(ChessDirection.combine(ChessDirection.cross(),ChessDirection.diagonal())),
    QUEEN(ChessDirection.combine(ChessDirection.cross(),ChessDirection.diagonal()));

    private final List<ChessDirection> directions;

    PieceType(final List<ChessDirection> directions) {
        this.directions = directions;
    }

    public boolean contains(ChessDirection direction){
        return directions.contains(direction);
    }
}
