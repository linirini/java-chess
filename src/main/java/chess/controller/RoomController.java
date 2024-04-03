package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandFactory;
import chess.dto.CommandInfo;
import chess.service.RoomService;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class RoomController {
    private static final String NOT_IN_ROOM_YET = "아직 게임 방에 입장하지 않았습니다.";
    private final InputView inputView;
    private final OutputView outputView;
    private final RoomService roomService;
    private final GameController gameController;
    private final Map<CommandType, Command> commands;

    public RoomController(final InputView inputView, final OutputView outputView, final RoomService roomService, final GameController gameController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.roomService = roomService;
        this.gameController = gameController;
        commands = CommandFactory.getInstance().create();
    }

    public void enter() {
        outputView.printGameMessage();
        outputView.printRooms(roomService.findAll());
        enterGame();
    }

    private void enterGame() {
        CommandInfo commandInfo = requestUntilValid(this::requestRoom);
        try {
            long id = commands.get(commandInfo.type()).execute(commandInfo, roomService);
            gameController.run(id);
        } catch (IllegalArgumentException e) {
            outputView.printGameErrorMessage(e.getMessage());
            enterGame();
        }
    }

    private CommandInfo requestRoom() {
        CommandInfo commandInfo = readCommandInfo();
        if (!commandInfo.type().isCreate() && !commandInfo.type().isEnter()) {
            throw new IllegalArgumentException(NOT_IN_ROOM_YET);
        }
        return commandInfo;
    }

    private CommandInfo readCommandInfo() {
        return CommandInfo.from(requestUntilValid(inputView::readCommand));
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
