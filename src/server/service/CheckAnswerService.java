package server.service;

import server.controller.GameRoom;
import server.domain.Player;

import java.util.Map;

public class CheckAnswerService {
    private static final int SCORE_PER_ANSWER = 10;

    public boolean correctAnswer(GameRoom gameRoom, Player player, String msg) {
        String correctWord = gameRoom.getCurrentWord();

        if (msg.equalsIgnoreCase(correctWord)) {
            gameRoom.broadcastToRoom("[System] " + player.getName() + "님이 정답을 맞추셨습니다! (+" + SCORE_PER_ANSWER + "점)");
            addScore(gameRoom, player);

            return true;
        }
        else {
            return false;
        }
    }


    //일단 안 쓰는 메소드...인듯(후에 확정되면 삭제)
    public boolean compareWord (GameRoom gameRoom, String word) {
        String correctWord = gameRoom.getCurrentWord();
        if(correctWord == null || word == null)
            return false;
        return word.equalsIgnoreCase(correctWord);
    }

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
