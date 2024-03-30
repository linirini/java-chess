package chess.domain.room;

import chess.domain.Name;

public class Room {
    private final Long id;
    private final Name name;

    public Room(final String name){
        this(null, new Name(name));
    }

    public Room(final Long id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }
}
