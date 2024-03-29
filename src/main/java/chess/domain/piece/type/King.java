package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessDirection;
import chess.domain.position.PieceRelation;
import chess.domain.position.Position;

public final class King extends Piece {
    public King(final PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isMovableDirection(source, target) && isMovableDistance(source, target);
    }

    private boolean isMovableDirection(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        return type.contains(chessDirection);
    }

    private boolean isMovableDistance(final Position source, final Position target) {
        int distance = source.calculateDistanceTo(target);
        return distance == 1;
    }

    private ChessDirection findDirection(final Position source, final Position target) {
        int fileDifference = source.calculateFileDifferenceTo(target);
        int rankDifference = source.calculateRankDifferenceTo(target);
        return ChessDirection.findDirection(fileDifference, rankDifference);
    }
}
