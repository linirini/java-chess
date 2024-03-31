package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.view.OutputView;

public class StatusCommand implements Command {
    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService) {
        outputView.printGameStatus(game.getWinningInfo());
    }
}
