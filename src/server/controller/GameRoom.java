package server.controller;

import server.CommandFactory;
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
        if(drawer == null) {
            drawer = p; // 첫번째로 들어오는 사람 자동으로 drawer 배정
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
        commandFactory.createCommand(this, msg, player);
    }

    //브로드캐스트 메소드-아마 건드릴거 없을듯?
    public void broadcastToRoom (String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    //이거 여기 있어야하는 메소드가 맞나...? ㅇㅅㅇ?
    public Player selectNextDrawer() {

        // GameRoom에서 플레이어 리스트 가져옴
        List<Player> players = this.players;
        if(players == null || players.isEmpty())
            return null; // 방이 비었으면 null

        // 지금 그림 그리는 사람
        Player currentPlayer = this.drawer;
        int currentIndex = players.indexOf(currentPlayer);

        // 예외 처리: 그림 그리는 사람 없거나 중간에 퇴장했으면 첫번째 사람으로 ,,
        if(currentIndex == -1)
            return players.get(0);
            // 다음 그림 그리는 사람
        else{
            int nextIndex = (currentIndex + 1) % players.size();
            return players.get(nextIndex);

        }
    }


    //getter & setter
    public String getCurrentWord() {return currentWord;}
    public void setCurrentWord(String currentWord) {this.currentWord = currentWord;}
    public void setDrawer (Player player) {this.drawer = player;}
    public Player getDrawer () {return this.drawer;}
    public List<Player> getPlayers () {return this.players;}
    public DrawService getDrawService() {return this.drawService;}
    public GameService getGameService() {return this.gameService;}
}
