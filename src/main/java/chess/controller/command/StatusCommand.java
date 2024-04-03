package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.service.RoomService;
import chess.view.OutputView;

public class StatusCommand implements Command {
    private static final String NOT_IN_GAME_YET = "아직 게임에 입장하지 않았습니다.";

    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService) {
        outputView.printGameStatus(game.getWinningInfo());
    }

    @Override
    public long execute(final CommandInfo commandInfo, final RoomService roomService) {
        throw new IllegalArgumentException(NOT_IN_GAME_YET);
    }
}
