package server.service;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.repository.QuizWordRepository;

import java.util.List;
import java.util.Map;

public class GameService {
    private final DrawerService drawerService;
    private final GameWordService gameWordService;
    private final CheckAnswerService checkAnswerService;
    private final WinnerService winnerService;
    private final QuizWordRepository quizWordRepository;


    public GameService(DrawerService drawerService, GameWordService gameWordService,
                       CheckAnswerService checkAnswerService, WinnerService winnerService,
                       QuizWordRepository quizWordRepository) {
        this.drawerService = drawerService;
        this.gameWordService = gameWordService;
        this.checkAnswerService = checkAnswerService;
        this.winnerService = winnerService;
        this.quizWordRepository = quizWordRepository;
    }

    public void startGame(GameRoom gameRoom) {
        gameRoom.broadcastToRoom("[System] 게임이 시작되었습니다!");
        nextRound(gameRoom);
    }

    public void endGame(GameRoom gameRoom) {
        gameRoom.broadcastToRoom(("CHAT: 게임이 종료되었습니다."));
        List<Map.Entry<ConnectionController,Integer>> scoreList = winnerService.getScoreList(gameRoom.getPlayers());

        if(scoreList.isEmpty()) return;

        String result = "RESULT:";
        //check
        System.out.println("CHECKKK");
        for (Map.Entry<ConnectionController,Integer> e : scoreList) {
            System.out.println(e.getKey().getPlayer().getName()+" "+e.getValue());
        }

        boolean winner = true;
        for (Map.Entry<ConnectionController, Integer> e : scoreList) {
            result += e.getKey().getPlayer().getName()+" "+e.getValue()+"점:";
            e.getKey().updatePlayerState(winner);
            winner = false;
        }
        gameRoom.broadcastToRoom("CHAT:승자는 ["+scoreList.get(0).getKey().getPlayer().getName()+"] 입니다");
        System.out.println(result);
        gameRoom.broadcastToRoom(result);
    }

    public void checkAnswer(GameRoom room, ConnectionController sender, String data) {
        String message = "CHAT:";
        if (checkAnswerService.correctAnswer(room, sender, data)) {
            nextRound(room);
        }
        message += "[" + sender.getName() + "] "+data;
        room.broadcastToRoom(message);
    }

    // 다음 라운드 준비
    public void nextRound(GameRoom gameRoom) {
        gameRoom.stopTimer();
        int round = gameRoom.getRound();
        gameRoom.setRound(++round);

        if (round > gameRoom.getPlayers().size()*3){
            gameRoom.stopTimer();
            endGame(gameRoom);
            return;
        }

        // 다음 그림 그리는 사람 선택
        ConnectionController newDrawer = drawerService.selectNextDrawer(gameRoom.getPlayers(), gameRoom.getDrawer());

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

        for (ConnectionController p : gameRoom.getPlayers()) {
            if (!p.equals(gameRoom.getDrawer()))
                p.send("[System] 새로운 라운드가 시작되었습니다!");
        }
        gameRoom.broadcastToRoom("다음 그림 그리는 사람은 "+newDrawer.getPlayer().getName()+"님 입니다.");

        gameRoom.startTimer();
    }

    public ConnectionController selectNextDrawer(GameRoom room) {
        return drawerService.selectNextDrawer(room.getPlayers(), room.getDrawer());
    }
    // 제시어 바꾸기
    private void changeWord(GameRoom gameRoom) {
        String nextWord = gameWordService.changeWord(gameRoom, quizWordRepository);
        gameRoom.setCurrentWord(nextWord);
    }
}