package chess.domain.board;

import chess.domain.Score;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardStatus {
    private final Map<Position, Piece> boardStatus;

    public BoardStatus(final Map<Position, Piece> boardStatus) {
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
        System.out.println("scoreExceptPawn" + totalScore);
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
        System.out.println("defaultPawnScore" + totalScore);
        return totalScore;
    }

    private double calculateHalfScore(final List<Integer> pawnCounts) {
        double totalScore = 0;
        List<Integer> multiplePawnOnFile = pawnCounts.stream().filter(count -> count > 1).toList();
        for (final Integer count : multiplePawnOnFile) {
            totalScore += count * PieceType.PAWN.halfScore();
        }
        System.out.println("halfPawnScore" + totalScore);
        return totalScore;
    }

    private int countPawns(final PieceColor color, final ChessFile file) {
        return (int) boardStatus.entrySet().stream()
                .filter(entry -> entry.getKey().isFile(file) && entry.getValue().isColor(color) && entry.getValue().isPawn())
                .count();
    }
}
