package chess;

import chess.controller.GameController;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        final GameController gameController = new GameController(InputView.getInstance(), OutputView.getInstance());
        gameController.run();
    }
}
