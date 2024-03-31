package chess.dto;

import java.util.List;

public record CommandInfo(CommandType type, List<String> arguments) {

    public static CommandInfo from(List<String> command) {
        CommandType commandType = CommandType.findByCommand(command);
        if (commandType.isMove()) {
            return new CommandInfo(commandType, List.of(command.get(1), command.get(2)));
        }
        if (commandType.isCreate() || commandType.isEnter()) {
            return new CommandInfo(commandType, List.of(command.get(1)));
        }
        return new CommandInfo(commandType, List.of());
    }
}
