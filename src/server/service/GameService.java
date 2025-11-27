package server.service;

import server.controller.GameController;
import server.domain.Player;
import server.repository.QuizWordRepository;

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

    public void startGame(GameController gameController) {
        gameController.broadcastToRoom("[System] 게임이 시작되었습니다!");
        nextRound(gameController);
    }

    public void checkAnswer(GameController room, Player sender, String data) {
        String message = "CHAT:";
        if (checkAnswerService.correctAnswer(room, sender, data)) {
            nextRound(room);
        }
        message += "[" + sender.getName() + "] "+data;
        room.broadcastToRoom(message);
    }

    public int getPlayerScore(GameController gameController, Player player) {
        Integer score = gameController.getScoreBoard().get(player);
        if (score == null) {
            return 0;
        }
        return score;

    }

    // 다음 라운드 준비
    public void nextRound(GameController gameController) {
        gameController.stopTimer();
        // 다음 그림 그리는 사람 선택
        Player newDrawer = drawerService.selectNextDrawer(gameController.getPlayers(), gameController.getDrawer());

        if (newDrawer == null) {
            gameController.broadcastToRoom("[System] 플레이어가 없어 게임을 진행할 수 없습니다.");
            return;
        }

        gameController.setDrawer(newDrawer);
        gameController.broadcastToRoom("ERASE:");

        // 사용자 업데이트
        drawerService.updatePlayerStates(gameController, newDrawer);

        // 제시어 변경
        changeWord(gameController);

        for (Player p : gameController.getPlayers()) {
            if (!p.equals(gameController.getDrawer()))
                p.sendMessage("[System] 새로운 라운드가 시작되었습니다!");
        }
        gameController.broadcastToRoom("다음 그림 그리는 사람은 "+newDrawer.getName()+"님 입니다.");

        gameController.startTimer();
    }

    public Player selectNextDrawer(GameController room) {
        return drawerService.selectNextDrawer(room.getPlayers(), room.getDrawer());
    }
    // 제시어 바꾸기
    private void changeWord(GameController gameController) {
        String nextWord = gameWordService.changeWord(gameController, quizWordRepository);
        gameController.setCurrentWord(nextWord);
    }
}