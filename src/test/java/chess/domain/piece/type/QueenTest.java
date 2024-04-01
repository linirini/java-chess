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

class QueenTest {

    static Stream<Arguments> cannotQueenMoveAllDirectionArguments() {
        return Stream.of(
                Arguments.arguments(Position.of("d4"), Position.of("d1")),
                Arguments.arguments(Position.of("d4"), Position.of("g4")),
                Arguments.arguments(Position.of("d4"), Position.of("d7")),
                Arguments.arguments(Position.of("d4"), Position.of("a4")),
                Arguments.arguments(Position.of("d4"), Position.of("g7")),
                Arguments.arguments(Position.of("d4"), Position.of("a7")),
                Arguments.arguments(Position.of("d4"), Position.of("g1")),
                Arguments.arguments(Position.of("d4"), Position.of("a1"))
        );
    }

    static Stream<Arguments> cannotMoveL_ShapeDirectionArguments() {
        return Stream.of(
                Arguments.arguments(Position.of("d4"), Position.of("c2")),
                Arguments.arguments(Position.of("d4"), Position.of("e2")),
                Arguments.arguments(Position.of("d4"), Position.of("c6")),
                Arguments.arguments(Position.of("d4"), Position.of("e6")),
                Arguments.arguments(Position.of("d4"), Position.of("f3")),
                Arguments.arguments(Position.of("d4"), Position.of("f5")),
                Arguments.arguments(Position.of("d4"), Position.of("b5")),
                Arguments.arguments(Position.of("d4"), Position.of("b3"))
        );
    }

    @DisplayName("퀸은 상하좌우, 대각선 방향으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("cannotQueenMoveAllDirectionArguments")
    void canQueenMoveCrossAndDiagonalDirection(Position source, Position target) {
        // given
        Piece queen = new Queen(PieceColor.BLACK);
        PieceRelation relation = PieceRelation.NONE;

        // when
        boolean result = queen.isMovable(source, target, relation);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("퀸은 상하좌우, 대각선 방향 외에는 움직일 수 없다.")
    @ParameterizedTest
    @MethodSource("cannotMoveL_ShapeDirectionArguments")
    void cannotQueenMoveLDirection(Position source, Position target) {
        // given
        Piece queen = new Queen(PieceColor.BLACK);
        PieceRelation relation = PieceRelation.NONE;

        // when
        boolean result = queen.isMovable(source, target, relation);

        // then
        assertThat(result).isFalse();
    }
}
