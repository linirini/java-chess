package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessDirection;
import chess.domain.position.ChessRank;
import chess.domain.position.PieceRelation;
import chess.domain.position.Position;

public final class Pawn extends Piece {

    public Pawn(final PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isMovableDirection(source, target, pieceRelation) && isMovableDistance(source, target);
    }

    private boolean isMovableDirection(final Position source, final Position target, final PieceRelation pieceRelation) {
        if (pieceRelation.isEnemy()) {
            return canAttack(source, target);
        }
        return canMove(source, target);
    }

    private boolean canAttack(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        if (color.isWhite()) {
            return chessDirection.isDiagonal() && chessDirection.isUpSide();
        }
        return chessDirection.isDiagonal() && chessDirection.isDownSide();
    }

    private boolean canMove(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        if (color.isWhite()) {
            return chessDirection.isVertical() && chessDirection.isUpSide();
        }
        return chessDirection.isVertical() && chessDirection.isDownSide();
    }

    private boolean isMovableDistance(final Position source, final Position target) {
        int distance = source.calculateDistanceTo(target);
        ChessDirection chessDirection = findDirection(source, target);
        if (color.isWhite() && source.isRank(ChessRank.TWO) && chessDirection == ChessDirection.UP) {
            return (distance == 1 || distance == 2);
        }
        if (color.isBlack() && source.isRank(ChessRank.SEVEN) && chessDirection == ChessDirection.DOWN) {
            return (distance == 1 || distance == 2);
        }
        return distance == 1;
    }

    private ChessDirection findDirection(final Position source, final Position target) {
        int fileDifference = source.calculateFileDifferenceTo(target);
        int rankDifference = source.calculateRankDifferenceTo(target);
        return ChessDirection.findDirection(fileDifference, rankDifference);
    }
}
