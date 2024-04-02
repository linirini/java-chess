package chess.domain.game;

import chess.domain.Score;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceScore;
import chess.domain.position.ChessFile;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameResult {
    private static final long KING_COUNT = 2;
    private static final String NO_KING = "King이 존재하지 않습니다.";

    private final Map<Position, Piece> pieces;

    public GameResult(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Score calculateScore(final PieceColor color) {
        double totalScore = scoreExceptPawn(color) + scoreOfPawn(color);
        return new Score(totalScore);
    }

    private double scoreExceptPawn(final PieceColor color) {
        double totalScore = 0;
        List<Piece> pieces = this.pieces.values().stream()
                .filter(piece -> piece.isColor(color) && !piece.isPawn())
                .toList();
        for (final Piece piece : pieces) {
            totalScore += PieceScore.findScore(piece).value();
        }
        return totalScore;
    }

    private double scoreOfPawn(final PieceColor color) {
        List<Integer> pawnCounts = new ArrayList<>();
        for (final ChessFile file : ChessFile.values()) {
            pawnCounts.add(countPawns(color, file));
        }
        return calculateDefaultScore(pawnCounts) + calculateHalfScore(pawnCounts);
    }

    private double calculateDefaultScore(final List<Integer> pawnCounts) {
        double totalScore = 0;
        List<Integer> onePawnOnFile = pawnCounts.stream().filter(count -> count == 1).toList();
        for (final Integer count : onePawnOnFile) {
            totalScore += count * PieceScore.PAWN.getScore().value();
        }
        return totalScore;
    }

    private double calculateHalfScore(final List<Integer> pawnCounts) {
        double totalScore = 0;
        List<Integer> multiplePawnOnFile = pawnCounts.stream().filter(count -> count > 1).toList();
        for (final Integer count : multiplePawnOnFile) {
            totalScore += count * PieceScore.PAWN.getScore().halfValue();
        }
        return totalScore;
    }

    private int countPawns(final PieceColor color, final ChessFile file) {
        return (int) pieces.entrySet().stream()
                .filter(entry -> entry.getKey().isFile(file) && entry.getValue().isColor(color) && entry.getValue().isPawn())
                .count();
    }

    public WinningResult determineWinningResult() {
        if (isKingAttacked()) {
            return WinningResult.findWinnerByColor(findKing().color());
        }
        return determineWinnerByScore();
    }

    private Piece findKing() {
        return pieces.values().stream()
                .filter(Piece::isKing)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NO_KING));
    }

    private WinningResult determineWinnerByScore() {
        Score whiteScore = calculateScore(PieceColor.WHITE);
        Score blackScore = calculateScore(PieceColor.BLACK);
        if (whiteScore.isBiggerThan(blackScore)) {
            return WinningResult.findWinnerByColor(PieceColor.WHITE);
        }
        if (blackScore.isBiggerThan(whiteScore)) {
            return WinningResult.findWinnerByColor(PieceColor.BLACK);
        }
        return WinningResult.TIE;
    }

    public boolean isKingAttacked() {
        long count = pieces.values().stream()
                .filter(Piece::isKing)
                .count();
        return count != KING_COUNT;
    }
}
