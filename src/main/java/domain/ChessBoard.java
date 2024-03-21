package domain;

import domain.piece.ChessBoardGenerator;
import domain.piece.Piece;
import domain.position.Position;
import dto.BoardStatus;

import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> board;

    public ChessBoard(final ChessBoardGenerator chessBoardGenerator) {
        this(chessBoardGenerator.generate());
    }

    public ChessBoard(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public BoardStatus status() {
        return BoardStatus.from(board);
    }

    public void move(String from, String to) {
        Position source = new Position(from);
        Position target = new Position(to);

        validate(source, target);

        Piece sourcePiece = board.get(source);
        board.put(target, sourcePiece);
        board.remove(source);
    }

    private void validate(Position source, Position target) {
        validatePosition(source, target);
        validateTarget(source, target);
        validateMovement(source, target);
    }

    private void validatePosition(Position source, Position target) {
        if (!isExist(source) || isSamePosition(source, target)) {
            throw new IllegalArgumentException("입력하신 이동 위치가 올바르지 않습니다.");
        }
    }

    private boolean isSamePosition(Position source, Position target) {
        return source.equals(target);
    }

    private void validateTarget(Position source, Position target) {
        if (isExist(target) && isSameColor(board.get(source), board.get(target))) {
            throw new IllegalArgumentException("이동할 수 없는 target입니다.");
        }
    }

    private boolean isSameColor(Piece sourcePiece, Piece targetPiece) {
        return targetPiece.isColor(sourcePiece.color());
    }

    private boolean isExist(Position target) {
        return board.containsKey(target);
    }

    private void validateMovement(Position source, Position target) {
        Piece sourcePiece = board.get(source);
        if (!sourcePiece.isInMovableRange(source, target)) {
            throw new IllegalArgumentException("기물이 이동할 수 없는 방식입니다.");
        }
    }
}
