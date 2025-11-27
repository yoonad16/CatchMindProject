package server.command;

import server.controller.GameController;
import server.domain.Player;

public class NameCommand implements Command {

    private String data = "NAME:";

    @Override
    public void create(String data, Player player) {
        String[] tokens = data.split(":");
        this.data = tokens[1];
    }

    @Override
    public void execute(GameController gameController, Player player) {
        player.setName(this.data);
        gameController.broadcastToRoom("CHAT:["+this.data+"]"+"님이 입장하셨습니다.");
    }
}
