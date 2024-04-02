package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceRelation;
import chess.domain.position.ChessDirection;
import chess.domain.position.ChessRank;
import chess.domain.position.Position;

import java.util.Set;

public final class Pawn extends Piece {
    private static final Set<ChessDirection> WHITE_DIRECTIONS = ChessDirection.upSide();
    private static final Set<ChessDirection> BLACK_DIRECTIONS = ChessDirection.downSide();

    public Pawn(final PieceColor color) {
        super(color);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isMovableDirection(source, target, pieceRelation) && isMovableDistance(source, target);
    }

    @Override
    public boolean isPawn() {
        return true;
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
            return WHITE_DIRECTIONS.contains(chessDirection) && chessDirection.isDiagonal();
        }
        return BLACK_DIRECTIONS.contains(chessDirection) && chessDirection.isDiagonal();
    }

    private boolean canMove(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        if (color.isWhite()) {
            return WHITE_DIRECTIONS.contains(chessDirection) && chessDirection.isVertical();
        }
        return BLACK_DIRECTIONS.contains(chessDirection) && chessDirection.isVertical();
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
