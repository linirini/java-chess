package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Pawn;
import chess.domain.piece.type.Rook;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ChessBoardTest {

    @DisplayName("보드 정보를 통해 체스보드를 생성한다")
    @Test
    void createChessBoard() {
        assertThatCode(() -> new ChessBoard(ChessBoardGenerator.getInstance().generate()))
                .doesNotThrowAnyException();
    }

    @DisplayName("체스 보드는 기물을 움직일 수 있다.")
    @Test
    void move() {
        // given
        ChessBoard chessBoard = new ChessBoard(ChessBoardGenerator.getInstance().generate());
        Position source = Position.of("b2");
        Piece piece = chessBoard.status().get(source);
        Position target = Position.of("b3");

        // when
        chessBoard.move(source, target);

        // then
        Map<Position, Piece> piecesInfo = chessBoard.status();

        assertAll(
                () -> assertThat(piecesInfo.get(source)).isNull(),
                () -> assertThat(piecesInfo.get(target)).isEqualTo(piece)
        );
    }

    @DisplayName("Source와 Target이 같은 색이면 이동할 수 없다.")
    @Test
    void isTargetSameColor() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position source = Position.of("b2");
        Position target = Position.of("c3");

        board.put(source, new Pawn(PieceColor.WHITE));
        board.put(target, new Pawn(PieceColor.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatThrownBy(() -> chessBoard.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("target으로 이동할 수 없습니다.");
    }

    @DisplayName("Source와 Target이 다른 색이면 이동할 수 있다.")
    @Test
    void isTargetNotSameColor() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position source = Position.of("b2");
        Position target = Position.of("c3");

        board.put(source, new Pawn(PieceColor.WHITE));
        board.put(target, new Pawn(PieceColor.BLACK));

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatCode(() -> chessBoard.move(source, target)).doesNotThrowAnyException();
    }

    @DisplayName("기물이 이동할 수 없는 방식으로 움직이면 예외를 발생한다.")
    @Test
    void validatePieceMovement() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position source = Position.of("b2");
        Position target = Position.of("b7");
        board.put(source, new Pawn(PieceColor.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatThrownBy(() -> chessBoard.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 이동할 수 없는 방식입니다.");
    }

    @DisplayName("나이트는 Source와 Target 사이에 다른 기물이 존재해도 이동할 수 있다.")
    @Test
    void canKnightMoveWhenBlocked() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position source = Position.of("b2");
        Position target = Position.of("c4");

        board.put(source, new Knight(PieceColor.WHITE));
        board.put(Position.of("b3"), new Pawn(PieceColor.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatCode(() -> chessBoard.move(source, target)).doesNotThrowAnyException();
    }

    @DisplayName("나이트 외에는 Source와 Target 사이에 다른 기물이 존재하면 이동할 수 없다.")
    @Test
    void cannotMoveWhenBlocked() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position source = Position.of("b2");
        Position target = Position.of("b4");

        board.put(source, new Rook(PieceColor.WHITE));
        board.put(Position.of("b3"), new Pawn(PieceColor.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatThrownBy(() -> chessBoard.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 이동할 수 없는 방식입니다.");
    }

    @DisplayName("주어진 위치에 기물이 존재한다.")
    @Test
    void pieceExists() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = Position.of("b2");

        board.put(position, new Rook(PieceColor.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);

        // when
        boolean result = chessBoard.isExist(position);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("주어진 위치에 기물이 존재한다.")
    @Test
    void pieceDoesNotExist() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = Position.of("b2");

        ChessBoard chessBoard = new ChessBoard(board);

        // when
        boolean result = chessBoard.isExist(position);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("주어진 위치에 있는 기물의 색깔을 반환한다.")
    @Test
    void findColorOfPiece() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = Position.of("b2");
        PieceColor expectedColor = PieceColor.BLACK;

        board.put(position, new Rook(expectedColor));

        ChessBoard chessBoard = new ChessBoard(board);

        // when
        PieceColor color = chessBoard.findColorOfPiece(position);

        // then
        assertThat(color).isEqualTo(expectedColor);
    }

    @DisplayName("주어진 위치에 기물이 없을 때 색깔을 반환하려고 하면 예외를 던진다.")
    @Test
    void cannotFindColorOfPiece() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = Position.of("b2");

        ChessBoard chessBoard = new ChessBoard(board);

        // when & then
        assertThatThrownBy(() -> chessBoard.findColorOfPiece(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주어진 위치에 기물이 존재하지 않습니다.");
    }
}
