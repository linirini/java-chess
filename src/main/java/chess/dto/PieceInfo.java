package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.PieceType;

public record PieceInfo(int fileIndex, int rankIndex, String pieceType) {
    public static PieceInfo of(final Position position, final Piece piece) {
        return new PieceInfo(position.indexOfFile(), position.indexOfRank(), PieceType.findType(piece).name());
    }
}
