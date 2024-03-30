package chess;

import chess.controller.GameController;
import chess.controller.RoomController;
import chess.repository.RoomDao;
import chess.repository.RoomRepository;
import chess.service.RoomService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        RoomRepository roomRepository = new RoomDao();
        RoomService roomService = new RoomService(roomRepository);
        GameController gameController = new GameController(InputView.getInstance(), OutputView.getInstance());
        RoomController roomController = new RoomController(InputView.getInstance(), OutputView.getInstance(), roomService, gameController);
        roomController.enter();
    }
}
