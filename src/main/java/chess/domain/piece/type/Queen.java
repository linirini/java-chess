package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessDirection;
import chess.domain.position.PieceRelation;
import chess.domain.position.Position;


public final class Queen extends Piece {

    public Queen(final PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean isMovable(final Position source, final Position target, final PieceRelation pieceRelation) {
        return isMovableDirection(source, target);
    }

    private boolean isMovableDirection(final Position source, final Position target) {
        ChessDirection chessDirection = findDirection(source, target);
        return type.contains(chessDirection);
    }

    private ChessDirection findDirection(final Position source, final Position target) {
        int fileDifference = source.calculateFileDifferenceTo(target);
        int rankDifference = source.calculateRankDifferenceTo(target);
        return ChessDirection.findDirection(fileDifference, rankDifference);
    }
}
