package server.controller;

import server.domain.Player;
import server.service.DrawService;
import server.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class GameRoom {
    List<Player> players = new ArrayList<>();
    String currentWord = "집";
    ConnectionController drawer;
    Timer gameTimer;
    Map<ConnectionController, Integer> scoreBoard;
    private final DrawService drawService = new DrawService();
    private final GameService gameService = new GameService();

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        broadcastToRoom(p.getName()+"님이 방을 나가셨습니다.");
    }

    public void processMessage (Player player, String msg) {

        if(msg.startsWith("DRAW")){
            drawService.sendDrawDate(msg);
        }
        else if (msg.startsWith("NAME")){
            gameService.nameMessage(msg, player);
        }
        else{
            gameService.answerMessage(msg, player);
        }
    }

    public void broadcastToRoom (String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
}
