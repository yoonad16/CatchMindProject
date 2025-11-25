package server.controller;

import java.util.*;
import server.command.CommandFactory;
import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

public class GameRoom {
    private final List<Player> players = new ArrayList<>();
    private String currentWord = "집";
    private Player drawer;
    Timer gameTimer;
    private int remainingTime = 30;
    Map<Player, Integer> scoreBoard = new HashMap<>();
    private final DrawService drawService;
    private final GameService gameService;
    private final CommandFactory commandFactory = CommandFactory.getInstance();

    public GameRoom (DrawService drawService, GameService gameService) {
        this.gameService = gameService;
        this.drawService = drawService;
    }


    //게임룸에 플레이어 추가/삭제 로직
    public void addPlayer(Player p) {
        players.add(p);
        scoreBoard.put(p, 0); // 점수 초기값 = 0
        if(drawer == null) {
            drawer = p; // 첫번째로 들어오는 사람 자동으로 drawer 배정
        }
        if (players.size() == 1) {
//            gameService.startGame(this);
        }
    }
    public void removePlayer(Player p) {
        players.remove(p);
        scoreBoard.remove(p);
        
        // drawer가 나간 경우 다음 drawer 선택
        if (drawer != null && drawer.equals(p)) {
            if (players.isEmpty()) {
                drawer = null;
            } else {
                drawer = selectNextDrawer();
            }
        }
        
        broadcastToRoom(p.getName()+"님이 방을 나가셨습니다.");
        System.out.println(p.getName()+"님이 방을 나가셨습니다.");
    }

    //메시지 처리 관련 로직
    //들어오는 메시지 1차 처리(각 서비스로 전달)
    public void processMessage (Player player, String msg) {
        commandFactory.createCommand(this, msg, player);
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
        if (players.isEmpty()) {
            return null;
        }
        
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
    public Map<Player, Integer> getScoreBoard() {return scoreBoard;}
    public List<Player> getPlayers () {return this.players;}
    public DrawService getDrawService() {return this.drawService;}
    public GameService getGameService() {return this.gameService;}
    
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
