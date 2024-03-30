package chess.domain.game;

import chess.domain.piece.PieceColor;

public enum WinningResult {
    BLACK_WIN,
    WHITE_WIN,
    TIE;

    public static WinningResult findWinnerByColor(final PieceColor color) {
        if (color.isBlack()) {
            return BLACK_WIN;
        }
        return WHITE_WIN;
    }
}
