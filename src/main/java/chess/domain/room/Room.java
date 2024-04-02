package chess.domain.room;

import chess.domain.Name;

public class Room {
    private static final int MINIMUM_NAME_LENGTH = 1;
    private static final int MAXIMUM_NAME_LENGTH = 10;
    private static final String INVALID_NAME_LENGTH = String.format("이름은 %d자 이상, %d자 이하만 가능합니다.", MINIMUM_NAME_LENGTH, MAXIMUM_NAME_LENGTH);

    private final Long id;
    private final Name name;

    public Room(final Long id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public static Room from(final String name) {
        validateName(name);
        return new Room(null, new Name(name));
    }

    private static void validateName(final String value) {
        if (value.length() < MINIMUM_NAME_LENGTH || value.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException(INVALID_NAME_LENGTH);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }
}
