package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;
import server.service.GameService;

public class ChatCommand implements Command{
    private String header = "CHAT:";
    private String data;

    @Override
    public void create(String msg, ConnectionController player) {
        String[] tokens = msg.split(":");

        this.data = tokens[1];
        System.out.println("header:"+header);
        System.out.println("data:"+data);
    }

    @Override
    public void execute(GameRoom gameRoom, ConnectionController player) {
        String message = header + "[" + player.getName()+ "] " +data;
        GameService gameService = gameRoom.getGameService();
        gameService.checkAnswer(gameRoom, player, data);
    }
}