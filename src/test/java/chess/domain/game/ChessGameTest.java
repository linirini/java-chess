package chess.domain.game;

import chess.domain.board.SettedBoardGenerator;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Pawn;
import chess.domain.piece.type.Rook;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ChessGameTest {

    @DisplayName("같은 색상의 기물을 연속해서 움직일 수 없다.")
    @Test
    void isNotTurn() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.of("b2"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("a1"), new Rook(PieceColor.WHITE));

        SettedBoardGenerator settedBoardGenerator = new SettedBoardGenerator(board);

        ChessGame chessGame = new ChessGame(settedBoardGenerator);

        Position source = Position.of("b2");
        Position target = Position.of("b3");

        chessGame.move(source, target);

        Position nextTarget = Position.of("b4");
        // when & then
        assertThatThrownBy(() -> chessGame.move(target, nextTarget))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PieceColor.WHITE.name() + "의 차례가 아닙니다.");
    }

    @DisplayName("Source에 기물이 존재하지 않으면 이동할 수 없다.")
    @Test
    void notExistSource() {
        // given
        SettedBoardGenerator settedBoardGenerator = new SettedBoardGenerator(Map.of());

        ChessGame chessGame = new ChessGame(settedBoardGenerator);

        Position source = Position.of("b2");
        Position target = Position.of("b3");

        // when & then
        assertThatThrownBy(() -> chessGame.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("source에 이동할 수 있는 기물이 존재하지 않습니다.");
    }

    @DisplayName("Source와 Target이 같으면 이동할 수 없다.")
    @Test
    void isSamePosition() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.of("b3"), new Pawn(PieceColor.WHITE));

        SettedBoardGenerator settedBoardGenerator = new SettedBoardGenerator(board);

        ChessGame chessGame = new ChessGame(settedBoardGenerator);

        Position source = Position.of("b3");
        Position target = Position.of("b3");

        // when & then
        assertThatThrownBy(() -> chessGame.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("source와 target이 같을 수 없습니다.");
    }

    @DisplayName("게임이 종료되지 않았으면 거짓을 반환한다.")
    @Test
    void isGameNotOver() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.of("b3"), new King(PieceColor.WHITE));
        board.put(Position.of("b4"), new King(PieceColor.BLACK));

        SettedBoardGenerator settedBoardGenerator = new SettedBoardGenerator(board);

        ChessGame chessGame = new ChessGame(settedBoardGenerator);

        // when
        boolean result = chessGame.isTerminated();

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("게임이 종료되었으면 참을 반환한다.")
    @Test
    void isGameOver() {
        // given
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.of("b3"), new King(PieceColor.WHITE));

        SettedBoardGenerator settedBoardGenerator = new SettedBoardGenerator(board);

        ChessGame chessGame = new ChessGame(settedBoardGenerator);

        // when
        boolean result = chessGame.isTerminated();

        // then
        assertThat(result).isTrue();
    }
}
