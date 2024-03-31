package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.view.OutputView;

public class StartCommand implements Command {
    private static final String GAME_ALREADY_STARTED = "게임 도중 start 명령어를 입력할 수 없습니다.";

    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService) {
        throw new IllegalArgumentException(GAME_ALREADY_STARTED);
    }
}
