package server.controller;

import java.util.*;
import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

public class GameRoom {
    private final List<Player> players = new ArrayList<>();
    private String currentWord = "집";
    private Player drawer;
    Timer gameTimer;
    TimerTask timerTask;
    private static final int DRAWING_TIME_LIMIT = 30; // 30초
    Map<ConnectionController, Integer> scoreBoard;
    private final DrawService drawService;
    private final GameService gameService;

    public GameRoom (DrawService drawService, GameService gameService) {
        this.gameService = gameService;
        this.drawService = drawService;
    }


    //게임룸에 플레이어 추가/삭제 로직
    public void addPlayer(Player p) {
        players.add(p);
        if(drawer == null) {
            drawer = p; // 첫번째로 들어오는 사람 자동으로 drawer 배정
        }
        if (players.size() == 1) {
//            gameService.startGame(this);
        }
    }
    public void removePlayer(Player p) {
        players.remove(p);
        broadcastToRoom(p.getName()+"님이 방을 나가셨습니다.");
        System.out.println(p.getName()+"님이 방을 나가셨습니다.");
    }

    //메시지 처리 관련 로직
    //들어오는 메시지 1차 처리(각 서비스로 전달)
    public void processMessage (Player player, String msg) {
        if(msg.startsWith("DRAW")){
            drawService.sendDrawDate(this, msg);
        }
        else if (msg.startsWith("NAME")){
            gameService.nameMessage(this, msg, player);
        }
        else if (msg.startsWith("ERASE")){
            gameService.eraseMessage(this, msg);
        }
        else{
            String[] tokens = msg.split(":");
            if(tokens.length > 1) {
                msg = tokens[1];
            }
            gameService.answerMessage(this, msg, player);
        }
    }

    //브로드캐스트 메소드-아마 건드릴거 없을듯?
    public void broadcastToRoom (String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    // GameRoom이 플레이어 목록 갖고있어서 여기로 옮김.
    // GameService에 두면 player, drawer 계속 참조해야해서 객체 간 결합도 증가해서 옮겼다~~라고 쓰면될듯

    public Player selectNextDrawer() {
        if (drawer == null) {
            return players.get(0);
        }
        int currentIndex = players.indexOf(drawer);

        if (currentIndex == -1)
            return players.get(0);

        return players.get((currentIndex + 1) % players.size());
    }


    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
    public void setDrawer (Player player) {this.drawer = player;}
    public Player getDrawer () {return this.drawer;}
    public List<Player> getPlayers () {return this.players;}
    public DrawService getDrawService() {return this.drawService;}
    public GameService getGameService() {return this.gameService;}

    // 타이머 관리 메서드
    public void startDrawingTimer() {
        cancelDrawingTimer(); // 기존 타이머가 있으면 취소
        
        gameTimer = new Timer();
        final GameRoom gameRoom = this; // 외부 변수 캡처를 위해 final 변수 사용
        
        // 클라이언트에 타이머 시작 신호 전송
        broadcastToRoom("TIMER:START:" + DRAWING_TIME_LIMIT);
        
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("[DEBUG] 그림 그리기 시간 제한(30초)이 만료되었습니다.");
                gameRoom.broadcastToRoom("[System] 시간이 초과되었습니다. 다음 라운드로 진행합니다.");
                gameRoom.broadcastToRoom("TIMER:STOP");
                gameService.nextRound(gameRoom);
            }
        };
        
        gameTimer.schedule(timerTask, DRAWING_TIME_LIMIT * 1000); // 30초 후 실행
        System.out.println("[DEBUG] 그림 그리기 타이머 시작: 30초");
    }

    public void cancelDrawingTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer.purge();
            gameTimer = null;
            // 클라이언트에 타이머 중지 신호 전송
            broadcastToRoom("TIMER:STOP");
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
