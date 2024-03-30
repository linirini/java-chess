package chess.service;

import chess.domain.room.Room;
import chess.dto.RoomInfos;
import chess.repository.RoomRepository;

import java.util.List;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public long findIdByName(final String name) {
        Room room = roomRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format("'%s'이라는 이름의 방을 찾을 수 없습니다.", name)));
        return room.getId();
    }

    public long create(final String name) {
        if (roomRepository.isExistName(name)) {
            throw new IllegalArgumentException(String.format("'%s'은 이미 존재하는 방 이름입니다.", name));
        }
        Room room = new Room(name);
        return roomRepository.save(room);
    }

    public RoomInfos findAll() {
        List<Room> rooms = roomRepository.findAll();
        return RoomInfos.of(rooms);
    }
}
