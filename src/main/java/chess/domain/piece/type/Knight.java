package chess.domain.piece.type;

import chess.domain.board.Movement;
import chess.domain.board.SquareStatus;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;

public final class Knight extends Piece {
    public Knight(final PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean isMovable(final Movement movement, final SquareStatus targetStatus) {
        return isLShapeMovement(movement);
    }

    public boolean isLShapeMovement(final Movement movement) {
        int fileDistance = movement.calculateFileDistance();
        int rankDistance = movement.calculateRankDistance();

        return (fileDistance == 1 && rankDistance == 2) || (fileDistance == 2 && rankDistance == 1);
    }
}
