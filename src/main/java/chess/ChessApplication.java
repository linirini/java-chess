package chess;

import chess.controller.GameController;
import chess.controller.RoomController;
import chess.repository.BoardDao;
import chess.repository.BoardRepository;
import chess.repository.RoomDao;
import chess.repository.RoomRepository;
import chess.service.GameService;
import chess.service.RoomService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        RoomRepository roomRepository = new RoomDao();
        BoardRepository boardRepository = new BoardDao();

        RoomService roomService = new RoomService(roomRepository);
        GameService gameService = new GameService(boardRepository, roomRepository);

        GameController gameController = new GameController(InputView.getInstance(), OutputView.getInstance(), gameService);
        RoomController roomController = new RoomController(InputView.getInstance(), OutputView.getInstance(), roomService, gameController);

        roomController.enter();
    }
}
