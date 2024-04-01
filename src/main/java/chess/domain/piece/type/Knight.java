package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceRelation;
import chess.domain.position.ChessDirection;
import chess.domain.position.Position;

import java.util.Set;

public final class Knight extends Piece {
    private static final Set<ChessDirection> DIRECTIONS = ChessDirection.LShapedDirections();

    public Knight(final PieceColor color) {
        super(color);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isLShapeMovement(source, target);
    }

    public boolean isLShapeMovement(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        return DIRECTIONS.contains(chessDirection);
    }

    private ChessDirection findDirection(final Position source, final Position target) {
        int fileDifference = source.calculateFileDifferenceTo(target);
        int rankDifference = source.calculateRankDifferenceTo(target);
        return ChessDirection.findDirection(fileDifference, rankDifference);
    }
}
