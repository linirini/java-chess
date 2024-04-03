package chess.controller.command;

import chess.view.CommandType;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private final static CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public Map<CommandType, Command> create() {
        Map<CommandType, Command> commands = new HashMap<>();
        commands.put(CommandType.START, new StartCommand());
        commands.put(CommandType.MOVE, new MoveCommand());
        commands.put(CommandType.STATUS, new StatusCommand());
        commands.put(CommandType.END, new EndCommand());
        commands.put(CommandType.CREATE, new CreateCommand());
        commands.put(CommandType.ENTER, new EnterCommand());
        return commands;
    }
}
