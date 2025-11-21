package server.controller;

import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

import java.util.*;

public class GameRoom {
    private final List<Player> players = new ArrayList<>();
    private String currentWord = "집";
    private Player drawer;
    Timer gameTimer;
    private final Map<Player, Integer> scoreBoard = new HashMap<>();
    private DrawService drawService;
    private GameService gameService;

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
    }
    public void removePlayer(Player p) {
        players.remove(p);
        scoreBoard.remove(p);
        broadcastToRoom(p.getName()+"님이 방을 나가셨습니다.");
        System.out.println(p.getName()+"님이 방을 나가셨습니다.");
    }

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
            msg = tokens[1];
            gameService.answerMessage(this, msg, player);
        }
    }

    //브로드캐스트 메소드-아마 건드릴거 없을듯?
    public void broadcastToRoom (String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    //이거 여기 있어야하는 메소드가 맞나...? ㅇㅅㅇ?
    // GameRoom이 플레이어 목록 갖고있어서 여기로 옮김.
    // GameService에 두면 player, drawer 계속 참조해야해서 객체 간 결합도 증가해서 옮겼다~~라고 쓰면될듯

    public Player selectNextDrawer() {
        if (players.isEmpty())
            return null;

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
}
