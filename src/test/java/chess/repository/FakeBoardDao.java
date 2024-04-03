package chess.repository;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.dto.PieceType;
import chess.domain.piece.type.*;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeBoardDao implements BoardRepository {
    private static final Map<Long, Piece> pieces = new ConcurrentHashMap<>();

    static {
        pieces.put(1L, new Bishop(PieceColor.WHITE));
        pieces.put(2L, new King(PieceColor.WHITE));
        pieces.put(3L, new Knight(PieceColor.WHITE));
        pieces.put(4L, new Queen(PieceColor.WHITE));
        pieces.put(5L, new Rook(PieceColor.WHITE));
        pieces.put(6L, new Pawn(PieceColor.WHITE));
        pieces.put(7L, new Bishop(PieceColor.BLACK));
        pieces.put(8L, new King(PieceColor.BLACK));
        pieces.put(9L, new Knight(PieceColor.BLACK));
        pieces.put(10L, new Queen(PieceColor.BLACK));
        pieces.put(11L, new Rook(PieceColor.BLACK));
        pieces.put(12L, new Pawn(PieceColor.WHITE));
    }

    private final Map<Long, FakeBoard> boards = new ConcurrentHashMap<>();

    @Override
    public void save(final Position position, final Piece piece, final long roomId) {
        long id = boards.size() + 1L;
        boards.put(id, new FakeBoard(position, piece, roomId));
    }

    @Override
    public void save(final Position position, final Long pieceId, final long roomId) {
        long id = boards.size() + 1L;
        boards.put(id, new FakeBoard(position, findPiece(pieceId), roomId));
    }

    @Override
    public boolean existsByRoomId(final long roomId) {
        return boards.values().stream().anyMatch(fakeBoard -> fakeBoard.roomId == roomId);
    }

    @Override
    public boolean existsByRoomIdAndPosition(final long roomId, final Position position) {
        return boards.values().stream()
                .anyMatch(value -> value.roomId == roomId && value.position.equals(position));
    }

    @Override
    public Map<Position, Piece> findPositionAndPieceByRoomId(final long roomId) {
        List<FakeBoard> fakeBoards = boards.values().stream()
                .filter(value -> value.roomId == roomId)
                .toList();
        Map<Position, Piece> pieces = new HashMap<>();
        for (final FakeBoard fakeBoard : fakeBoards) {
            pieces.put(fakeBoard.position, fakeBoard.piece);
        }
        return pieces;
    }

    @Override
    public Optional<Long> findPieceIdByRoomIdAndPosition(final long roomId, final Position position) {
        Optional<Piece> piece = boards.values().stream()
                .filter(value -> value.roomId == roomId && value.position.equals(position))
                .map(FakeBoard::piece)
                .findAny();
        if (piece.isPresent()) {
            return findId(piece.get());
        }
        return Optional.empty();
    }

    @Override
    public void deleteByRoomIdAndPosition(final long roomId, final Position position) {
        long id = boards.entrySet().stream()
                .filter(entry -> entry.getValue().roomId == roomId && entry.getValue().position.equals(position))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없습니다."));
        boards.remove(id);
    }

    private Piece findPiece(final Long pieceId) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getKey().equals(pieceId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 id입니다."));
    }

    private Optional<Long> findId(Piece piece) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isColor(piece.color()) && PieceType.findType(entry.getValue()) == PieceType.findType(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny();
    }

    private record FakeBoard(Position position, Piece piece, long roomId) {
    }
}
