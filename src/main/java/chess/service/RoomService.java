package chess.service;

import chess.domain.game.Turn;
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
        return roomRepository.findIdByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format("'%s'이라는 이름의 방을 찾을 수 없습니다.", name)));
    }

    public long create(final String name) {
        if (roomRepository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("'%s'은 이미 존재하는 방 이름입니다.", name));
        }
        Room room = Room.from(name);
        Turn turn = Turn.first();
        return roomRepository.save(room, turn);
    }

    public RoomInfos findAll() {
        List<Room> rooms = roomRepository.findAll();
        return RoomInfos.of(rooms);
    }
}
