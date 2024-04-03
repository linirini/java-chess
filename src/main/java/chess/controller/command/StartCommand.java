package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.service.RoomService;
import chess.view.OutputView;

public class StartCommand implements Command {
    private static final String GAME_ALREADY_STARTED = "게임 도중 start 명령어를 사용할 수 없습니다.";
    private static final String NOT_IN_GAME_YET = "아직 게임에 입장하지 않았습니다.";

    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService) {
        throw new IllegalArgumentException(GAME_ALREADY_STARTED);
    }

    @Override
    public long execute(final CommandInfo commandInfo, final RoomService roomService) {
        throw new IllegalArgumentException(NOT_IN_GAME_YET);
    }
}
