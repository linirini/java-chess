package chess.domain;

import java.util.Objects;

public class Score {
    private static final String INVALID_SCORE = "점수는 0 이상만 가능합니다.";

    private final double score;

    public Score(final double score) {
        validate(score);
        this.score = score;
    }

    private void validate(final double score) {
        if (score < 0) {
            throw new IllegalArgumentException(INVALID_SCORE);
        }
    }

    public boolean isBiggerThan(Score score) {
        return this.score > score.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Score score1 = (Score) o;
        return Double.compare(score1.score, score) == 0;
    }

    public double value() {
        return score;
    }

    public double halfValue() {
        return score / 2;
    }
}
