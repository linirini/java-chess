package chess.domain.piece.type;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceRelation;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BishopTest {

    static Stream<Arguments> cannotBishopMoveCrossArguments() {
        return Stream.of(
                Arguments.arguments(Position.of("d4"), Position.of("d3")),
                Arguments.arguments(Position.of("d4"), Position.of("h4")),
                Arguments.arguments(Position.of("d4"), Position.of("d7")),
                Arguments.arguments(Position.of("d4"), Position.of("a4"))
        );
    }

    static Stream<Arguments> canBishopMoveDiagonalArguments() {
        return Stream.of(
                Arguments.arguments(Position.of("d4"), Position.of("e5")),
                Arguments.arguments(Position.of("d4"), Position.of("c5")),
                Arguments.arguments(Position.of("d4"), Position.of("e3")),
                Arguments.arguments(Position.of("d4"), Position.of("c3"))
        );
    }

    @DisplayName("비숍은 상하좌우로 움직일 수 없다.")
    @ParameterizedTest
    @MethodSource("cannotBishopMoveCrossArguments")
    void cannotBishopMoveCross(Position source, Position target) {
        // given
        Piece bishop = new Bishop(PieceColor.BLACK);
        PieceRelation relation = PieceRelation.NONE;

        // when
        boolean result = bishop.isMovable(source, target, relation);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("비숍은 대각선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("canBishopMoveDiagonalArguments")
    void canBishopMoveDiagonal(Position source, Position target) {
        // given
        Piece bishop = new Bishop(PieceColor.BLACK);
        PieceRelation relation = PieceRelation.NONE;

        // when
        boolean result = bishop.isMovable(source, target, relation);

        // then
        assertThat(result).isTrue();
    }
}
