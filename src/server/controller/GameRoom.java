package server.controller;

import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class GameRoom {
    private final List<Player> players = new ArrayList<>();
    private String currentWord = "집";
    private Player drawer;
    Timer gameTimer;
    Map<ConnectionController, Integer> scoreBoard;
    private DrawService drawService;
    private GameService gameService;

    public GameRoom (DrawService drawService, GameService gameService) {
        this.gameService = gameService;
        this.drawService = drawService;
    }


    //게임룸에 플레이어 추가/삭제 로직
    public void addPlayer(Player p) {
        players.add(p);
    }
    public void removePlayer(Player p) {
        players.remove(p);
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


    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
    public void setDrawer (Player player) {this.drawer = player;}
    public Player getDrawer () {return this.drawer;}
    public List<Player> getPlayers () {return this.players;}
}
