package chess.dto;

import java.util.Arrays;
import java.util.List;

public enum CommandType {

    START("start", 0),
    MOVE("move", 2),
    STATUS("status", 0),
    END("end", 0);

    private static final String INVALID_COMMAND = "존재하지 않는 명령어 입니다.";
    private final String type;
    private final int argumentCount;

    CommandType(String type, int argumentCount) {
        this.type = type;
        this.argumentCount = argumentCount;
    }

    public static CommandType findByCommand(List<String> input) {
        return Arrays.stream(values())
                .filter(command -> command.type.equals(input.get(0)))
                .filter(command -> command.argumentCount == input.size() - 1)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND));
    }

    public static boolean doesNotExist(String type) {
        return Arrays.stream(values())
                .noneMatch(commandType -> commandType.type.equals(type));
    }

    public static boolean isInValidArgumentCount(List<String> input) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.type.equals(input.get(0)))
                .noneMatch(commandType -> commandType.argumentCount == input.size() - 1);
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isEnd() {
        return this == END;
    }

    public boolean isStatus() {
        return this == STATUS;
    }
}
