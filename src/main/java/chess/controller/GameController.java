package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandFactory;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.service.GameService;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class GameController {
    private static final String GAME_NOT_STARTED = "아직 게임이 시작되지 않았습니다.";

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<CommandType, Command> commands;
    private final GameService gameService;

    public GameController(final InputView inputView, final OutputView outputView, final GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
        this.commands = CommandFactory.getInstance().create();
    }

    public void run(long roomId) {
        outputView.printGameStartMessage();
        start();
        ChessGame game = gameService.findGame(roomId);
        outputView.printChessBoard(game.getBoardStatus());

        play(game);

        terminate(game);
    }

    private void start() {
        CommandInfo commandInfo;
        do {
            commandInfo = requestUntilValid(this::requestStart);
        } while (!commandInfo.type().isStart());
    }

    private CommandInfo requestStart() {
        CommandInfo commandInfo = readCommandInfo();
        if (!commandInfo.type().isStart()) {
            throw new IllegalArgumentException(GAME_NOT_STARTED);
        }
        return commandInfo;
    }

    private void play(final ChessGame game) {
        CommandInfo commandInfo = requestUntilValid(this::readCommandInfo);
        while (!commandInfo.type().isEnd()) {
            executeCommand(game, commandInfo);
            if (game.isTerminated()) {
                break;
            }
            commandInfo = requestUntilValid(this::readCommandInfo);
        }
    }

    private CommandInfo readCommandInfo() {
        return CommandInfo.from(requestUntilValid(inputView::readCommand));
    }

    private void executeCommand(final ChessGame game, final CommandInfo commandInfo) {
        try {
            commands.get(commandInfo.type()).execute(game, commandInfo, outputView, gameService);
        } catch (IllegalArgumentException e) {
            outputView.printGameErrorMessage(e.getMessage());
            play(game);
        }
    }

    private void terminate(final ChessGame game) {
        outputView.printEndMessage();
        outputView.printGameStatus(game.getWinningInfo());
    }

    private <T> T requestUntilValid(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryGet(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryGet(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.printGameErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
