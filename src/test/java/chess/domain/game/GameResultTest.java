package chess.domain.game;

import chess.domain.Score;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.*;
import chess.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GameResultTest {
    /**
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * <p>
     * abcdefgh
     */
    private Map<Position, Piece> board;

    @BeforeEach
    void init() {
        board = new HashMap<>();
        board.put(Position.of("b8"), new King(PieceColor.BLACK));
        board.put(Position.of("c8"), new Rook(PieceColor.BLACK));
        board.put(Position.of("a7"), new Pawn(PieceColor.BLACK));
        board.put(Position.of("c7"), new Pawn(PieceColor.BLACK));
        board.put(Position.of("d7"), new Bishop(PieceColor.BLACK));
        board.put(Position.of("b6"), new Pawn(PieceColor.BLACK));
        board.put(Position.of("e6"), new Queen(PieceColor.BLACK));
        board.put(Position.of("f4"), new Knight(PieceColor.WHITE));
        board.put(Position.of("g4"), new Queen(PieceColor.WHITE));
        board.put(Position.of("f3"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("h3"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("f2"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("g2"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("e1"), new Rook(PieceColor.WHITE));
        board.put(Position.of("f1"), new King(PieceColor.WHITE));
    }

    @DisplayName("각 진영의 점수를 계산한다.")
    @Test
    void calculateScore() {
        //given
        GameResult gameResult = new GameResult(board);
        Score expectedWhiteScore = new Score(19.5);
        Score expectedBlackScore = new Score(20);

        //when
        Score whiteScore = gameResult.calculateScore(PieceColor.WHITE);
        Score blackScore = gameResult.calculateScore(PieceColor.BLACK);

        //then
        assertAll(
                () -> assertThat(whiteScore).isEqualTo(expectedWhiteScore),
                () -> assertThat(blackScore).isEqualTo(expectedBlackScore)
        );
    }

    @DisplayName("킹이 하나라도 없을 경우, 공격당했다고 판단한다.")
    @Test
    void isKingAttacked() {
        //given
        Position whiteKingPosition = Position.of("f1");
        board.remove(whiteKingPosition);
        GameResult gameResult = new GameResult(board);

        //when
        boolean result = gameResult.isKingAttacked();

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("킹이 모두 존재할 경우, 공격당하지 않았다고 판단한다.")
    @Test
    void isKingNotAttacked() {
        //given
        GameResult gameResult = new GameResult(board);

        //when
        boolean result = gameResult.isKingAttacked();

        //then
        assertThat(result).isFalse();
    }

    /**
     * ..R.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * <p>
     * abcdefgh
     */
    @DisplayName("검정의 킹이 제거되어, 흰색 진영이 승리한다.")
    @Test
    void whiteWinBecauseBlackKingIsAttacked() {
        //given
        Position blackKingPosition = Position.of("b8");
        board.remove(blackKingPosition);
        GameResult gameResult = new GameResult(board);

        //when
        WinningResult winningResult = gameResult.determineWinningResult();

        //then
        assertThat(winningResult).isEqualTo(WinningResult.WHITE_WIN);
    }

    @DisplayName("킹이 모두 존재하고, 검정의 점수가 흰색의 점수보다 높아 검정 진영이 승리한다.")
    @Test
    void blackWinBecauseBlackScoreIsBigger() {
        //given
        GameResult gameResult = new GameResult(board);

        //when
        WinningResult winningResult = gameResult.determineWinningResult();

        //then
        assertThat(winningResult).isEqualTo(WinningResult.BLACK_WIN);
    }

    /**
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * .....p..  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * <p>
     * abcdefgh
     */
    @DisplayName("킹이 모두 존재하고, 검정의 점수와 흰색의 점수가 같으면 무승부이다.")
    @Test
    void tieBecauseScoreIsSame() {
        //given
        board.put(Position.of("f5"), new Pawn(PieceColor.WHITE));
        GameResult gameResult = new GameResult(board);

        //when
        WinningResult winningResult = gameResult.determineWinningResult();

        //then
        assertThat(winningResult).isEqualTo(WinningResult.TIE);
    }

    /**
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * .....p..  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * p...rk..  1
     * <p>
     * abcdefgh
     */
    @DisplayName("킹이 모두 존재하고, 흰색의 점수가 검정의 점수보다 높아 흰색 진영이 승리한다.")
    @Test
    void whiteWinBecauseScoreIsBigger() {
        //given
        board.put(Position.of("f5"), new Pawn(PieceColor.WHITE));
        board.put(Position.of("a1"), new Pawn(PieceColor.WHITE));
        GameResult gameResult = new GameResult(board);

        //when
        WinningResult winningResult = gameResult.determineWinningResult();

        //then
        assertThat(winningResult).isEqualTo(WinningResult.WHITE_WIN);
    }
}
