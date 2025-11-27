package server.service;

import server.controller.GameController;
import server.domain.Player;

import java.util.Map;

public class CheckAnswerService {
    private static final int SCORE_PER_ANSWER = 10;

    public boolean correctAnswer(GameController gameController, Player player, String msg) {
        String correctWord = gameController.getCurrentWord();

        if (msg.equalsIgnoreCase(correctWord)) {
            gameController.broadcastToRoom("[System] " + player.getName() + "님이 정답을 맞추셨습니다! (+" + SCORE_PER_ANSWER + "점)");
            addScore(gameController, player);

            return true;
        }
        else {
            return false;
        }
    }


    //일단 안 쓰는 메소드...인듯(후에 확정되면 삭제)
    public boolean compareWord (GameController gameController, String word) {
        String correctWord = gameController.getCurrentWord();
        if(correctWord == null || word == null)
            return false;
        return word.equalsIgnoreCase(correctWord);
    }

    public int getPlayerScore(GameController gameController, Player player) {
        Integer score = gameController.getScoreBoard().get(player);
        if (score == null) {
            return 0;
        }
        return score;

    }

    public void addScore(GameController gameController, Player player) {
        Map<Player, Integer> board = gameController.getScoreBoard();
        Integer currentScore = board.get(player);

        if (currentScore == null)
            currentScore = 0;

        board.put(player, board.get(player) + SCORE_PER_ANSWER);
    }
}
