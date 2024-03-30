package chess.service;

import chess.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public long findIdByName(final String name) {
        return roomRepository.findIdByName(name);
    }
}
