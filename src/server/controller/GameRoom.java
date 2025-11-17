package server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class GameRoom {
    //이거 Player 리스트로 바꾸기
    List<ConnectionController> players = new ArrayList<>();
    String currentWord = "집";
    ConnectionController drawer;
    Timer gameTimer;
    Map<ConnectionController, Integer> scoreBoard;

    public void addPlayer(ConnectionController c) {
        players.add(c);
    }

    public void removePlayer(ConnectionController c) {
        players.remove(c);
        broadcastToRoom(c.getPlayerName()+"님이 방을 나가셨습니다.");
    }

    //이거 메소드 정리해야할듯
    public void processMessage (ConnectionController sender, String msg) {
        if(msg.startsWith("DRAW")){
            broadcastToRoom(msg);
        }
        else if (msg.startsWith("NAME")){
            String[] tokens = msg.split(":");
            sender.getPlayer().setName(tokens[1]);
            broadcastToRoom(sender.getPlayerName()+"님이 방을 들어오셨습니다.");
        }
        else{
            if (msg.equalsIgnoreCase(currentWord)){
                msg = "["+sender.getPlayerName()+"]: "+msg;
                broadcastToRoom(msg);
                broadcastToRoom(sender.getPlayerName()+"님이 정답을 맞추셨습니다.");
            }
            else{
                msg = "["+sender.getPlayerName()+"]: "+msg;
                broadcastToRoom(msg);
            }
        }
    }

    public void broadcastToRoom (String msg) {
        for(ConnectionController c : players) {
            c.send(msg);
        }
    }

    //나중에 키워드 설정할 때 쓸 듯
    public void setCurrentWord(String word) {
        this.currentWord = word;
    }
}
