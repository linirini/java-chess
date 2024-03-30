package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;

public class SettedBoardGenerator implements BoardGenerator {

    private final Map<Position, Piece> board;

    public SettedBoardGenerator(final Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public Map<Position, Piece> generate() {
        return board;
    }
}
