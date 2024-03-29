package chess.domain.board;

import chess.domain.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.ChessDirection;
import chess.domain.position.PieceRelation;
import chess.domain.position.Position;
import chess.dto.BoardStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {
    private static final String INVALID_SOURCE = "source에 이동할 수 있는 기물이 존재하지 않습니다.";
    private static final String INVALID_POSITIONS = "source와 target이 같을 수 없습니다.";
    private static final String INVALID_TARGET = "target으로 이동할 수 없습니다.";
    private static final String INVALID_TURN = "%s의 차례가 아닙니다.";
    private static final String INVALID_MOVEMENT = "기물이 이동할 수 없는 방식입니다.";

    private final Map<Position, Piece> board;

    public ChessBoard(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public BoardStatus status() {
        return BoardStatus.from(board);
    }

    public void move(final String from, final String to, final Turn turn) {
        Position source = Position.of(from);
        Position target = Position.of(to);

        validatePosition(source, target);
        validateTarget(source, target);
        validateTurn(source, turn);
        validateMovement(source, target);

        updateBoard(source, target);
    }

    private void validatePosition(final Position source, final Position target) {
        validateSource(source);
        validateIdentity(source, target);
    }

    private void validateSource(final Position source) {
        if (!isExist(source)) {
            throw new IllegalArgumentException(INVALID_SOURCE);
        }
    }

    private boolean isExist(final Position position) {
        return board.containsKey(position);
    }

    private void validateIdentity(final Position source, final Position target) {
        if (source == target) {
            throw new IllegalArgumentException(INVALID_POSITIONS);
        }
    }

    private void validateTarget(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        PieceRelation relation = PieceRelation.determine(sourcePiece, board.get(target));
        if (relation.isPeer()) {
            throw new IllegalArgumentException(INVALID_TARGET);
        }
    }

    private void validateTurn(final Position source, final Turn turn) {
        Piece sourcePiece = board.get(source);
        if (turn.isNotTurnOwner(sourcePiece.color())) {
            throw new IllegalArgumentException(String.format(INVALID_TURN, sourcePiece.color()));
        }
    }

    private void validateMovement(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        PieceRelation relation = PieceRelation.determine(sourcePiece, board.get(target));
        if (!sourcePiece.isMovable(source, target, relation) || isBlocked(source,target)) {
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
        while(nextPosition!=target) {
            route.add(nextPosition);
            nextPosition = nextPosition.move(direction);
        }
        return route;
    }

    private void updateBoard(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        board.put(target, sourcePiece);
        board.remove(source);
    }
}
