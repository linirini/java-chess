package chess.domain.board;

import chess.domain.Score;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.type.*;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardStatusTest {
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
    @DisplayName("각 진영의 점수를 계산한다.")
    @Test
    void calculateScore() {
        //given
        Map<Position, Piece> board = new HashMap<>();
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

        BoardStatus boardStatus = new BoardStatus(board);
        Score expectedWhiteScore = new Score(19.5);
        Score expectedBlackScore = new Score(20);

        //when
        Score whiteScore = boardStatus.calculateScore(PieceColor.WHITE);
        Score blackScore = boardStatus.calculateScore(PieceColor.BLACK);

        //then
        assertAll(
                () -> assertThat(whiteScore).isEqualTo(expectedWhiteScore),
                () -> assertThat(blackScore).isEqualTo(expectedBlackScore)
        );
    }
}
