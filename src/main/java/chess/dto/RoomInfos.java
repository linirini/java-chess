package chess.dto;

import chess.domain.room.Room;

import java.util.ArrayList;
import java.util.List;

public record RoomInfos(List<String> names) {
    public static RoomInfos of(List<Room> rooms) {
        List<String> names = new ArrayList<>();
        for (final Room room : rooms) {
            names.add(room.getName());
        }
        return new RoomInfos(names);
    }
}
