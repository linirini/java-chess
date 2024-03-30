package chess.dto;

import chess.domain.game.Score;
import chess.domain.game.WinningResult;

public record GameStatus(double whiteScore, double blackScore, String winningResult) {

    public static GameStatus of(Score whiteScore, Score blackScore, WinningResult winningResult){
        return new GameStatus(whiteScore.score(), blackScore.score(), winningResult.name());
    }
}
