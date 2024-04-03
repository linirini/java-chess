package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.service.RoomService;
import chess.view.OutputView;

public interface Command {
    void execute(final ChessGame game, final CommandInfo commandInfo, final OutputView outputView, final GameService gameService);

    long execute(final CommandInfo commandInfo, final RoomService roomService);
}
