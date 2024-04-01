package chess.domain.game;

import chess.domain.Score;
import chess.domain.board.BoardGenerator;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.position.Position;
import chess.dto.BoardStatus;
import chess.dto.GameStatus;

import java.util.Map;

public class ChessGame {
    private static final String INVALID_SOURCE = "source에 이동할 수 있는 기물이 존재하지 않습니다.";
    private static final String INVALID_POSITIONS = "source와 target이 같을 수 없습니다.";
    private static final String INVALID_TURN = "%s의 차례가 아닙니다.";

    private final Long roomId;
    private final ChessBoard board;
    private final Turn turn;

    public ChessGame(final BoardGenerator boardGenerator) {
        this(null, boardGenerator.generate(), Turn.first());
    }

    public ChessGame(final Long roomId, final Map<Position, Piece> board, Turn turn) {
        this.roomId = roomId;
        this.board = new ChessBoard(board);
        this.turn = turn;
    }

    public static ChessGame create(final long roomId, final BoardGenerator boardGenerator) {
        return new ChessGame(roomId, boardGenerator.generate(), Turn.first());
    }

    public static ChessGame enter(final Long roomId, final Map<Position, Piece> board, Turn turn) {
        return new ChessGame(roomId, board, turn);
    }

    public void move(final Position source, final Position target) {
        validateSource(source);
        validateTurn(source);
        validateIdentity(source, target);

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

    public BoardStatus getBoardStatus() {
        Map<Position, Piece> pieceInfos = board.status();
        return BoardStatus.from(pieceInfos);
    }

    public GameStatus getWinningInfo() {
        GameResult gameResult = new GameResult(board.status());
        Score whiteScore = gameResult.calculateScore(PieceColor.WHITE);
        Score blackScore = gameResult.calculateScore(PieceColor.BLACK);
        WinningResult winningResult = gameResult.determineWinningResult();
        return GameStatus.of(whiteScore, blackScore, winningResult);
    }

    public boolean isTerminated() {
        GameResult gameResult = new GameResult(board.status());
        return gameResult.isKingAttacked();
    }

    public Turn turn() {
        return turn;
    }

    public long roomId() {
        return roomId;
    }
}
