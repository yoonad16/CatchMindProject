package server.command;

import server.controller.GameController;
import server.domain.Player;
import server.service.GameService;

public class ChatCommand implements Command{
    private String header = "CHAT:";
    private String data;

    @Override
    public void create(String msg, Player player) {
        String[] tokens = msg.split(":");

        this.data = tokens[1];
        System.out.println("header:"+header);
        System.out.println("data:"+data);
    }

    @Override
    public void execute(GameController gameController, Player player) {
        String message = header + "[" + player.getName()+ "] " +data;
        GameService gameService = gameController.getGameService();
        gameService.checkAnswer(gameController, player, data);
    }
}