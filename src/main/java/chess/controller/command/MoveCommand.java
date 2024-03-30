package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.view.OutputView;

public class MoveCommand implements Command {
    private static final int SOURCE_INDEX = 0;
    private static final int TARGET_INDEX = 1;

    @Override
    public void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView) {
        game.move(commandInfo.arguments().get(SOURCE_INDEX), commandInfo.arguments().get(TARGET_INDEX));
        outputView.printChessBoard(game.getBoardStatus());
    }
}
