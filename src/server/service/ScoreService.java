package server.service;

import server.controller.GameRoom;
import server.domain.Player;

import java.util.Map;

public class ScoreService {
    private static final int SCORE_PER_ANSWER = 10;

    public int getPlayerScore(GameRoom gameRoom, Player player) {
        Integer score = gameRoom.getScoreBoard().get(player);
        if (score == null) {
            return 0;
        }
        return score;

    }

    public void addScore(GameRoom gameRoom, Player player) {
        Map<Player, Integer> board = gameRoom.getScoreBoard();
        Integer currentScore = board.get(player);

        if (currentScore == null)
            currentScore = 0;

        board.put(player, board.get(player) + SCORE_PER_ANSWER);
    }

}
