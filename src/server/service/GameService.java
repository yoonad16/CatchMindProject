package server.service;

import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;
import server.repository.QuizWordRepository;

import java.util.List;
import java.util.Map;

public class GameService {
    // 정답 당 10점씩 올라가는걸로 일단 구현할게요
    private static final int SCORE_PER_ANSWER = 10;
    private final DrawerService drawerService;
    private final GameWordService gameWordService;
    private final CheckAnswerService checkAnswerService;
    private final QuizWordRepository quizWordRepository;

    public GameService(DrawerService drawerService, GameWordService gameWordService,
                       CheckAnswerService checkAnswerService, QuizWordRepository quizWordRepository) {
        this.drawerService = drawerService;
        this.gameWordService = gameWordService;
        this.checkAnswerService = checkAnswerService;
        this.quizWordRepository = quizWordRepository;
    }

    public void startGame(GameRoom gameRoom) {
        gameRoom.broadcastToRoom("[System] 게임이 시작되었습니다!");
        nextRound(gameRoom);
    }

    public void checkAnswer(GameRoom room, Player sender, String data) {
        String message = "CHAT:";
        if (checkAnswerService.correctAnswer(room, sender, data)) {
            nextRound(room);
        }
        message += "[" + sender.getName() + "] "+data;
        room.broadcastToRoom(message);
    }

    public int getPlayerScore(GameRoom gameRoom, Player player) {
        Integer score = gameRoom.getScoreBoard().get(player);
        if (score == null) {
            return 0;
        }
        return score;

    }

    // 다음 라운드 준비
    public void nextRound(GameRoom gameRoom) {
        // 다음 그림 그리는 사람 선택
        Player newDrawer = drawerService.selectNextDrawer(gameRoom.getPlayers(), gameRoom.getDrawer());

        if (newDrawer == null) {
            gameRoom.broadcastToRoom("[System] 플레이어가 없어 게임을 진행할 수 없습니다.");
            return;
        }

        gameRoom.setDrawer(newDrawer);
        gameRoom.broadcastToRoom("ERASE:");

        // 사용자 업데이트
        drawerService.updatePlayerStates(gameRoom, newDrawer);

        // 제시어 변경
        changeWord(gameRoom);

        for (Player p : gameRoom.getPlayers()) {
            if (!p.equals(gameRoom.getDrawer()))
                p.sendMessage("[System] 새로운 라운드가 시작되었습니다!");
        }
        gameRoom.broadcastToRoom("다음 그림 그리는 사람은 "+newDrawer.getName()+"님 입니다.");

    }

    public Player selectNextDrawer(GameRoom room) {
        return drawerService.selectNextDrawer(room.getPlayers(), room.getDrawer());
    }
    // 제시어 바꾸기
    private void changeWord(GameRoom gameRoom) {
        String nextWord = gameWordService.changeWord(gameRoom, quizWordRepository);
        gameRoom.setCurrentWord(nextWord);
    }
}