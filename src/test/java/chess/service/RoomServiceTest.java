package chess.service;

import chess.domain.Name;
import chess.domain.room.Room;
import chess.repository.FakeRoomDao;
import chess.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Room room = new Room(name);
        long id = roomRepository.save(room);

        //when
        Room result = roomService.findByName(name);

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName()).isEqualTo(name)
        );
    }

    @DisplayName("존재하지 않는 이름으로 게임방을 조회를 시도하면 예외를 발생시킨다.")
    @Test
    void isNotExistName() {
        //given
        String name = "리니방";

        //when & then
        assertThatThrownBy(() -> roomService.findByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'" + name + "'이라는 이름의 방을 찾을 수 없습니다.");
    }
}
