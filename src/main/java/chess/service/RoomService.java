package chess.service;

import chess.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public long findIdByName(final String name) {
        if (!roomRepository.isExistName(name)) {
            throw new IllegalArgumentException(String.format("'%s'이라는 이름의 방을 찾을 수 없습니다.", name));
        }
        return roomRepository.findIdByName(name);
    }
}
