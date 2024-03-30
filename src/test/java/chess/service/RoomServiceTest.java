package chess.service;

import chess.domain.Name;
import chess.domain.room.Room;
import chess.repository.FakeRoomDao;
import chess.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomServiceTest {
    RoomRepository roomRepository;
    RoomService roomService;

    @BeforeEach
    void init() {
        roomRepository = new FakeRoomDao();
        roomService = new RoomService(roomRepository);
    }

    @DisplayName("이름으로 게임방을 조회한다.")
    @Test
    void findIdByName() {
        //given
        String name = "리니방";
        long id = 1L;
        Room room = new Room(id, new Name(name));
        roomRepository.save(room);

        //when
        long result = roomService.findIdByName(name);

        //then
        assertThat(result).isEqualTo(id);
    }
}
