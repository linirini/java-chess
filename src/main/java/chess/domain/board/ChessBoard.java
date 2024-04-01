package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceRelation;
import chess.domain.position.ChessDirection;
import chess.domain.position.Position;

import java.util.*;

public class ChessBoard {
    private static final String INVALID_TARGET = "target으로 이동할 수 없습니다.";
    private static final String INVALID_MOVEMENT = "기물이 이동할 수 없는 방식입니다.";
    private static final String NO_PIECE = "주어진 위치에 기물이 존재하지 않습니다.";

    private final Map<Position, Piece> board;

    public ChessBoard(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(final Position source, final Position target) {
        validateTarget(source, target);
        validateMovement(source, target);

        updateBoard(source, target);
    }

    private void validateTarget(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        PieceRelation relation = PieceRelation.determine(sourcePiece, board.get(target));
        if (relation.isPeer()) {
            throw new IllegalArgumentException(INVALID_TARGET);
        }
    }

    private void validateMovement(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        PieceRelation relation = PieceRelation.determine(sourcePiece, board.get(target));
        if (!sourcePiece.isMovable(source, target, relation) || isBlocked(source, target)) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }

    private boolean isBlocked(final Position source, Position target) {
        List<Position> route = findRouteBetween(source, target);
        return route.stream().anyMatch(this::isExist);
    }

    private List<Position> findRouteBetween(final Position source, final Position target) {
        int fileDiff = source.calculateFileDifferenceTo(target);
        int rankDiff = source.calculateRankDifferenceTo(target);
        ChessDirection direction = ChessDirection.findDirection(fileDiff, rankDiff);
        List<Position> route = new ArrayList<>();
        Position nextPosition = source.move(direction);
        while (nextPosition != target) {
            route.add(nextPosition);
            nextPosition = nextPosition.move(direction);
        }
        return route;
    }

    public PieceColor findColorOfPiece(final Position position) {
        if (!isExist(position)) {
            throw new IllegalArgumentException(NO_PIECE);
        }
        return board.get(position).color();
    }

    public boolean isExist(final Position position) {
        return board.containsKey(position);
    }

    private void updateBoard(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        board.put(target, sourcePiece);
        board.remove(source);
    }

    public Map<Position, Piece> status() {
        return Collections.unmodifiableMap(board);
    }
}
