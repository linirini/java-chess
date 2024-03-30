package chess.view;

import chess.domain.game.WinningResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class WinningResultViewTest {

    @DisplayName("모든 WinningResult에 대응되는 WinningResultView를 이름으로 찾을 수 있다.")
    @Test
    void mappingPieceTypeToPieceTypeView() {
        assertThatCode(() -> Arrays.stream(WinningResult.values()).forEach(winningResult -> WinningResultView.findViewName(winningResult.name())))
                .doesNotThrowAnyException();
    }
}
