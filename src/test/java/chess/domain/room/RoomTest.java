package chess.domain.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomTest {

    @DisplayName("이름 길이가 1 이상, 10 이하이면 방이 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaa"})
    void createRoom(String name) {
        //when & then
        assertThatCode(() -> Room.from(name)).doesNotThrowAnyException();
    }

    @DisplayName("이름 길이가 1 미만, 10 초과이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaa"})
    void createInvalidRoom(String name) {
        //when & then
        assertThatThrownBy(() -> Room.from(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 1자 이상, 10자 이하만 가능합니다.");
    }
}
