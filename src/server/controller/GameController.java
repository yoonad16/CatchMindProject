package server.controller;

import java.util.*;
import server.command.CommandFactory;
import server.domain.Player;
import server.service.GameService;

public class GameController {
    private final List<Player> players = new ArrayList<>();
    private String currentWord;
    private Player drawer;
    Timer gameTimer;
    Map<Player, Integer> scoreBoard = new HashMap<>();
    private final GameService gameService;
    private final CommandFactory commandFactory = CommandFactory.getInstance();

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    //게임룸에 플레이어 추가/삭제 로직
    public void addPlayer(Player p) {
        players.add(p);
        scoreBoard.put(p, 0); // 점수 초기값 = 0
        if(drawer == null) {
            drawer = p; // 첫번째로 들어오는 사람 자동으로 drawer 배정
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
                drawer = gameService.selectNextDrawer(this);
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

    public void broadcastToRoom (String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
    public void setDrawer (Player player) {this.drawer = player;}
    public Player getDrawer () {return this.drawer;}
    public Map<Player, Integer> getScoreBoard() {return scoreBoard;}
    public List<Player> getPlayers () {return this.players;}
    public GameService getGameService() {return this.gameService;}
}
