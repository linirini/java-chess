package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessFileTest {

    static Stream<Arguments> moveArguments() {
        return java.util.stream.Stream.of(
                Arguments.arguments(ChessFile.C, ChessDirection.DOWN, ChessFile.C),
                Arguments.arguments(ChessFile.C, ChessDirection.UP, ChessFile.C),
                Arguments.arguments(ChessFile.C, ChessDirection.LEFT, ChessFile.B),
                Arguments.arguments(ChessFile.C, ChessDirection.RIGHT, ChessFile.D),
                Arguments.arguments(ChessFile.C, ChessDirection.DOWN_LEFT, ChessFile.B),
                Arguments.arguments(ChessFile.C, ChessDirection.DOWN_RIGHT, ChessFile.D),
                Arguments.arguments(ChessFile.C, ChessDirection.UP_LEFT, ChessFile.B),
                Arguments.arguments(ChessFile.C, ChessDirection.UP_RIGHT, ChessFile.D),
                Arguments.arguments(ChessFile.C, ChessDirection.UP_UP_LEFT, ChessFile.B),
                Arguments.arguments(ChessFile.C, ChessDirection.UP_UP_RIGHT, ChessFile.D),
                Arguments.arguments(ChessFile.C, ChessDirection.DOWN_DOWN_LEFT, ChessFile.B),
                Arguments.arguments(ChessFile.C, ChessDirection.DOWN_DOWN_RIGHT, ChessFile.D),
                Arguments.arguments(ChessFile.C, ChessDirection.RIGHT_RIGHT_UP, ChessFile.E),
                Arguments.arguments(ChessFile.C, ChessDirection.RIGHT_RIGHT_DOWN, ChessFile.E),
                Arguments.arguments(ChessFile.C, ChessDirection.LEFT_LEFT_UP, ChessFile.A),
                Arguments.arguments(ChessFile.C, ChessDirection.LEFT_LEFT_DOWN, ChessFile.A)
        );
    }

    @DisplayName("주어진 File의 값으로 File을 찾는다.")
    @Test
    void findByValue() {
        // given
        String fileValue = "a";

        // when
        ChessFile file = ChessFile.findByValue(fileValue);

        // then
        assertThat(file).isEqualTo(ChessFile.A);
    }

    @DisplayName("체스 파일 범위에 해당하지 않는 값을 조회하면 예외를 발생시킨다.")
    @Test
    void unknownChessFileValue() {
        // given
        String fileValue = "z";

        // when & then
        assertThatThrownBy(() -> ChessFile.findByValue(fileValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("체스 파일 범위에 해당하지 않는 값입니다.");
    }

    @DisplayName("체스 파일을 주어진 방향 정보에 따라 갱신한다.")
    @ParameterizedTest
    @MethodSource("moveArguments")
    void move(ChessFile before, ChessDirection chessDirection, ChessFile after) {
        //when & then
        assertThat(before.move(chessDirection)).isEqualTo(after);
    }
}
