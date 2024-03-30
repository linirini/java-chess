package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.view.OutputView;

public class EndCommand implements Command {
    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView) {
    }
}
