package server.controller;

import server.command.CommandFactory;
import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

import java.util.*;

public class GameRoom {
    private final List<Player> players = new ArrayList<>();
    private String currentWord = "집";
    private Player drawer;
    Timer gameTimer;
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
}
