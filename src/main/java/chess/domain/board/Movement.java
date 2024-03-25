package chess.domain.board;

import chess.domain.Direction;
import chess.domain.position.ChessRank;
import chess.domain.position.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Movement {
    private final Position source;
    private final Position target;

    public Movement(final Position source, final Position target) {
        this.source = source;
        this.target = target;
    }

    private static boolean isOnlyOneDirection(final int fileDiff, final int rankDiff) {
        return fileDiff == 0 || rankDiff == 0 || Math.abs(rankDiff) == Math.abs(fileDiff);
    }

    private static List<Direction> findOnlyOneDirections(final int fileDiff, final int rankDiff) {
        return Stream.generate(() -> Direction.findDirection(fileDiff, rankDiff))
                .limit(Math.max(Math.abs(rankDiff), Math.abs(fileDiff)))
                .toList();
    }

    private static List<Direction> findMultipleDirections(final int fileDiff, final int rankDiff) {
        Stream<Direction> horizontalDirections = Stream.generate(() -> Direction.findDirection(fileDiff, 0)).limit(fileDiff);
        Stream<Direction> verticalDirections = Stream.generate(() -> Direction.findDirection(0, rankDiff)).limit(rankDiff);
        if (Math.abs(fileDiff) > Math.abs(rankDiff)) {
            return Stream.concat(horizontalDirections, verticalDirections).toList();
        }
        return Stream.concat(verticalDirections, horizontalDirections).toList();
    }

    public Set<Position> findRoute() {
        Set<Position> route = new HashSet<>();
        Position nextPosition = source;
        for (final Direction direction : findDirections()) {
            System.out.println(direction.name());
            nextPosition = nextPosition.move(direction);
            System.out.println(nextPosition.indexOfFile() + " " + nextPosition.indexOfRank());
            if (nextPosition != target) {
                route.add(nextPosition);
            }
        }
        return route;
    }

    private List<Direction> findDirections() {
        int fileDiff = source.calculateFileDifferenceTo(target);
        int rankDiff = source.calculateRankDifferenceTo(target);
        if (isOnlyOneDirection(fileDiff, rankDiff)) {
            return findOnlyOneDirections(fileDiff, rankDiff);
        }
        return findMultipleDirections(fileDiff, rankDiff);
    }

    public boolean isDiagonal() {
        return findDirection().isDiagonal();
    }

    public boolean isCross() {
        return findDirection().isCross();
    }

    public Direction findDirection() {
        int fileDiff = source.calculateFileDifferenceTo(target);
        int rankDiff = source.calculateRankDifferenceTo(target);
        return Direction.findDirection(fileDiff, rankDiff);
    }

    public int calculateFileDistance() {
        return Math.abs(source.calculateFileDifferenceTo(target));
    }

    public int calculateRankDistance() {
        return Math.abs(source.calculateRankDifferenceTo(target));
    }

    public int calculateDistance() {
        return source.calculateDistanceTo(target);
    }

    public boolean isSourceRank(final ChessRank rank) {
        return source.isRank(rank);
    }
}
