package server.service;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;

public class CheckAnswerService {
    private static final int SCORE_PER_ANSWER = 10;

    public boolean correctAnswer(GameRoom gameRoom, ConnectionController session, String msg) {
        String correctWord = gameRoom.getCurrentWord();

        if (msg.equalsIgnoreCase(correctWord)) {
            gameRoom.broadcastToRoom("[System] " + session.getPlayer().getName() + "님이 정답을 맞추셨습니다! (+" + SCORE_PER_ANSWER + "점)");
            addScore(gameRoom, session);

            return true;
        }
        else {
            return false;
        }
    }

    public void addScore(GameRoom gameRoom, ConnectionController p) {
        Player player = p.getPlayer();

        int score = player.getScore();
        score += SCORE_PER_ANSWER;

        player.setScore(score);
    }
}
