package server.service;

import server.controller.GameRoom;
import server.domain.Player;

public class CheckAnswerService {
    private static final int SCORE_PER_ANSWER = 10;

    public void correctAnswer(Player player, GameRoom gameRoom, String msg) {

        gameRoom.broadcastToRoom("[" + player.getName() + "]: " + msg);
        gameRoom.broadcastToRoom("[System] " + player.getName() + "님이 정답을 맞추셨습니다! (+" + SCORE_PER_ANSWER + "점)");

        // 점수 추가
        addScore(gameRoom, player);
        // 다음 라운드 진행
        nextRound(gameRoom);
    }
}
