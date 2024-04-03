package chess.service;

import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;

import java.util.Map;

public class GameService {
    private static final String PIECE_NOT_FOUND = "해당 위치의 기물 정보를 찾을 수 없습니다.";
    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;

    public GameService(final BoardRepository boardRepository, final RoomRepository roomRepository) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
    }

    public ChessGame findGame(final long roomId) {
        if (isBoardNotReady(roomId)) {
            createBoard(roomId);
            return ChessGame.create(roomId, ChessBoardGenerator.getInstance());
        }
        Map<Position, Piece> pieces = boardRepository.findPositionAndPieceByRoomId(roomId);
        Turn turn = roomRepository.findTurnById(roomId).orElseThrow();
        return ChessGame.enter(roomId, pieces, turn);
    }

    private void createBoard(final long roomId) {
        for (final Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            boardRepository.save(position, piece, roomId);
        }
    }

    private boolean isBoardNotReady(final long roomId) {
        return !boardRepository.existsByRoomId(roomId);
    }

    public void move(final ChessGame game, final String from, final String to) {
        Position source = Position.of(from);
        Position target = Position.of(to);
        game.move(source, target);
        updateMovement(game, source, target);
        roomRepository.updateTurnByRoomId(game.roomId(), game.turn());
    }

    private void updateMovement(final ChessGame game, final Position source, final Position target) {
        Piece piece = boardRepository.findPieceByRoomIdAndPosition(game.roomId(), source)
                .orElseThrow(() -> new IllegalArgumentException(PIECE_NOT_FOUND));

        boardRepository.deleteByRoomIdAndPosition(game.roomId(), source);
        if (boardRepository.existsByRoomIdAndPosition(game.roomId(), target)) {
            boardRepository.deleteByRoomIdAndPosition(game.roomId(), target);
        }

        boardRepository.save(target, piece, game.roomId());
    }
}
