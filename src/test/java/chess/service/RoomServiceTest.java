package chess.service;

import chess.domain.game.Turn;
import chess.domain.room.Room;
import chess.dto.RoomInfos;
import chess.repository.FakeRoomDao;
import chess.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomServiceTest {
    RoomRepository roomRepository;
    RoomService roomService;
    Turn turn;

    @BeforeEach
    void init() {
        roomRepository = new FakeRoomDao();
        roomService = new RoomService(roomRepository);
        turn = Turn.first();
    }

    @DisplayName("이름으로 게임방을 조회한다.")
    @Test
    void findIdByName() {
        //given
        String name = "리니방";
        Room room = Room.from(name);
        long id = roomRepository.save(room, turn);

        //when
        long result = roomService.findIdByName(name);

        //then
        assertThat(result).isEqualTo(id);
    }

    @DisplayName("존재하지 않는 이름으로 게임방을 조회를 시도하면 예외를 발생시킨다.")
    @Test
    void isNotExistName() {
        //given
        String name = "리니방";

        //when & then
        assertThatThrownBy(() -> roomService.findIdByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'" + name + "'이라는 이름의 방을 찾을 수 없습니다.");
    }

    @DisplayName("이름으로 방을 생성할 수 있다.")
    @Test
    void createRoom() {
        //given
        String name = "리니방";

        //when
        roomService.create(name);

        //then
        assertThat(roomRepository.existsByName(name)).isTrue();
    }

    @DisplayName("중복된 이름으로 방을 생성하려고 하면 예외를 발생시킨다.")
    @Test
    void createDuplicatedRoom() {
        //given
        String name = "리니방";
        Room room = Room.from(name);
        roomRepository.save(room, turn);

        //when & then
        assertThatThrownBy(() -> roomService.create(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'" + name + "'은 이미 존재하는 방 이름입니다.");
    }

    @DisplayName("현재 존재하는 모든 방의 이름을 찾는다.")
    @Test
    void findAll() {
        //given
        List<String> names = List.of("리니방", "포비방", "찰리방");
        for (final String name : names) {
            Room room = Room.from(name);
            roomRepository.save(room, turn);
        }

        //when
        RoomInfos result = roomService.findAll();

        //then
        assertThat(result.names()).containsAll(names);
    }
}
