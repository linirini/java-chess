package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessDirection;
import chess.domain.position.PieceRelation;
import chess.domain.position.Position;

public final class Knight extends Piece {
    public Knight(final PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isLShapeMovement(source, target);
    }

    public boolean isLShapeMovement(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        return chessDirection.isLShaped();
    }

    private ChessDirection findDirection(final Position source, final Position target) {
        int fileDifference = source.calculateFileDifferenceTo(target);
        int rankDifference = source.calculateRankDifferenceTo(target);
        return ChessDirection.findDirection(fileDifference, rankDifference);
    }
}
