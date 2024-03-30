package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameResult {
    private static final long KING_COUNT = 2;
    private static final String NO_KING = "King이 존재하지 않습니다.";

    private final Map<Position, Piece> boardStatus;

    public GameResult(final Map<Position, Piece> boardStatus) {
        this.boardStatus = boardStatus;
    }

    public Score calculateScore(final PieceColor color) {
        double totalScore = scoreExceptPawn(color) + scoreOfPawn(color);
        return new Score(totalScore);
    }

    private double scoreExceptPawn(final PieceColor color) {
        double totalScore = 0;
        List<Piece> pieces = boardStatus.values().stream()
                .filter(piece -> piece.isColor(color) && !piece.isPawn())
                .toList();
        for (final Piece piece : pieces) {
            totalScore += piece.score();
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
            totalScore += count * PieceType.PAWN.score();
        }
        return totalScore;
    }

    private double calculateHalfScore(final List<Integer> pawnCounts) {
        double totalScore = 0;
        List<Integer> multiplePawnOnFile = pawnCounts.stream().filter(count -> count > 1).toList();
        for (final Integer count : multiplePawnOnFile) {
            totalScore += count * PieceType.PAWN.halfScore();
        }
        return totalScore;
    }

    private int countPawns(final PieceColor color, final ChessFile file) {
        return (int) boardStatus.entrySet().stream()
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
        return boardStatus.values().stream()
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
        long count = boardStatus.values().stream()
                .filter(Piece::isKing)
                .count();
        return count != KING_COUNT;
    }
}
