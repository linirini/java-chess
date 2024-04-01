package chess.domain.piece;

import chess.domain.Score;
import chess.domain.piece.type.*;

import java.util.Arrays;

public enum PieceScore {
    BISHOP(Bishop.class, new Score(3)),
    KING(King.class, new Score(0)),
    KNIGHT(Knight.class, new Score(2.5)),
    PAWN(Pawn.class, new Score(1)),
    QUEEN(Queen.class, new Score(9)),
    ROOK(Rook.class, new Score(5));

    private static final String INVALID_PIECE = "존재하지 않는 기물입니다.";
    private final Class<? extends Piece> pieceClass;
    private final Score score;

    PieceScore(final Class<? extends Piece> pieceClass, final Score score) {
        this.pieceClass = pieceClass;
        this.score = score;
    }

    public static Score findScore(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceScore -> pieceScore.pieceClass.isInstance(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PIECE))
                .score;
    }

    public Score getScore() {
        return score;
    }
}
