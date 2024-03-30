package chess.controller;

import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfo;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Optional;
import java.util.function.Supplier;

public class GameController {
    private static final String GAME_NOT_STARTED = "아직 게임이 시작되지 않았습니다.";
    private static final String GAME_ALREADY_STARTED = "게임 도중 start 명령어를 입력할 수 없습니다.";
    private static final int SOURCE_INDEX = 0;
    private static final int TARGET_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;

    public GameController(final InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameStartMessage();
        start();
        ChessGame game = new ChessGame(ChessBoardGenerator.getInstance());
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
        CommandInfo commandInfo = requestUntilValid(this::requestMove);
        while (!commandInfo.type().isEnd()) {
            if (commandInfo.type().isMove()) {
                playTurn(game, commandInfo);
            }
            if (commandInfo.type().isStatus()) {
                outputView.printGameStatus(game.getWinningInfo());
            }
            if (game.isTerminated()) {
                break;
            }
            commandInfo = requestUntilValid(this::requestMove);
        }
    }

    private CommandInfo requestMove() {
        CommandInfo commandInfo = readCommandInfo();
        if (commandInfo.type().isStart()) {
            throw new IllegalArgumentException(GAME_ALREADY_STARTED);
        }
        return commandInfo;
    }

    private CommandInfo readCommandInfo() {
        return CommandInfo.from(requestUntilValid(inputView::readCommand));
    }

    private void playTurn(final ChessGame game, final CommandInfo commandInfo) {
        try {
            game.move(commandInfo.arguments().get(SOURCE_INDEX), commandInfo.arguments().get(TARGET_INDEX));
            outputView.printChessBoard(game.getBoardStatus());
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
