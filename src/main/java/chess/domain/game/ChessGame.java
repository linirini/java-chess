package chess.domain.game;

import chess.domain.board.BoardGenerator;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.position.Position;
import chess.dto.BoardStatus;

import java.util.Map;

public class ChessGame {
    private static final String INVALID_SOURCE = "source에 이동할 수 있는 기물이 존재하지 않습니다.";
    private static final String INVALID_POSITIONS = "source와 target이 같을 수 없습니다.";
    private static final String INVALID_TURN = "%s의 차례가 아닙니다.";

    private final ChessBoard board;
    private final Turn turn;

    public ChessGame(final BoardGenerator boardGenerator) {
        this.board = new ChessBoard(boardGenerator.generate());
        this.turn = Turn.first();
    }

    public void move(final String from, final String to) {
        Position source = Position.of(from);
        Position target = Position.of(to);

        validateSource(source);
        validateTurn(source);
        validateIdentity(source,target);

        board.move(source, target);
        turn.next();
    }

    private void validateSource(final Position source) {
        if (!board.isExist(source)) {
            throw new IllegalArgumentException(INVALID_SOURCE);
        }
    }

    private void validateTurn(final Position source) {
        PieceColor color = board.findColorOfPiece(source);
        if (turn.isNotTurnOwner(color)) {
            throw new IllegalArgumentException(String.format(INVALID_TURN, color));
        }
    }

    private void validateIdentity(final Position source, final Position target) {
        if (source == target) {
            throw new IllegalArgumentException(INVALID_POSITIONS);
        }
    }

    public BoardStatus getBoardStatus(){
        Map<Position, Piece> pieceInfos = board.status();
        return BoardStatus.from(pieceInfos);
    }
}
