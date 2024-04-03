package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.service.RoomService;
import chess.view.OutputView;

public class EnterCommand implements Command {
    private static final String GAME_ALREADY_STARTED = "게임 도중 enter 명령어를 사용할 수 없습니다.";
    private static final int NAME_INDEX = 0;

    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService) {
        throw new IllegalArgumentException(GAME_ALREADY_STARTED);
    }

    @Override
    public long execute(final CommandInfo commandInfo, final RoomService roomService) {
        return roomService.findIdByName(commandInfo.arguments().get(NAME_INDEX));
    }
}
