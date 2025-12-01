package server.controller;

import java.util.*;
import server.command.CommandFactory;
import server.domain.Player;
import server.service.GameService;

public class GameRoom {
//    private final List<Player> players = new ArrayList<>();
    private final List<ConnectionController> players = new ArrayList<>();
    private String currentWord;
    private ConnectionController drawer;
    Timer gameTimer;
    private int remainingTime = 30;
    private final GameService gameService;
    private final CommandFactory commandFactory = CommandFactory.getInstance();
    private int round;

    public GameRoom(GameService gameService) {
        this.gameService = gameService;
    }


    //게임룸에 플레이어 추가/삭제 로직
    public void addPlayer(ConnectionController p) {
        players.add(p);
        p.getPlayer().setScore(0);

        if(drawer == null) {
            drawer = p; // 첫번째로 들어오는 사람 자동으로 drawer 배정
        }
    }
    public void removePlayer(ConnectionController p) {
        if(players.size() <= 1) {
            System.out.println("남은 인원 1명 이하임");
            gameService.endGame(this);
        }
        else p.send("DISCONNECT:");

        players.remove(p);

        // drawer가 나간 경우 다음 drawer 선택
        if (drawer != null && drawer.equals(p)) {
            if (players.isEmpty()) {
                drawer = null;
            } else {
                drawer = gameService.selectNextDrawer(this);
            }
        }

        broadcastToRoom(p.getName()+"님이 방을 나가셨습니다.");
        System.out.println(p.getName()+"님이 방을 나가셨습니다.");


    }

    public void updatePlayerStatus(List<Map.Entry<ConnectionController,Integer>> scoreList) {

    }

    //메시지 처리 관련 로직
    //들어오는 메시지 1차 처리(각 서비스로 전달)
    public void processMessage (ConnectionController player, String msg) {
        commandFactory.createCommand(this, msg, player);
    }

    public void broadcastToRoom (String msg) {
        for(ConnectionController p : players) {
            p.send(msg);
        }
    }

    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
    public void setDrawer (ConnectionController player) {this.drawer = player;}
    public ConnectionController getDrawer () {return this.drawer;}
    public List<ConnectionController> getPlayers () {return this.players;}
    public GameService getGameService() {return this.gameService;}
    public int getRound() {return round;}
    public void setRound(int round) {this.round = round;}

    // 타이머 관련 메소드
    public void startTimer() {
        // 기존 타이머가 있으면 취소
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        remainingTime = 30;
        gameTimer = new Timer();

        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    broadcastToRoom("TIMER:" + remainingTime);
                    remainingTime--;
                } else {
                    // 시간 종료 시 다음 라운드로
                    gameTimer.cancel();
                    broadcastToRoom("[System] 시간이 종료되었습니다!");
                    gameService.nextRound(GameRoom.this);
                }
            }
        }, 0, 1000); // 0초 후 시작, 1초마다 실행
    }

    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }
}
