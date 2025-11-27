package server.command;

import server.controller.GameController;
import server.domain.Player;

public class EraseCommand implements Command {
    private String data;

    @Override
    public void create(String data, Player player) {
        this.data = data;
    }
    @Override
    public void execute(GameController gameController, Player player) {
        gameController.broadcastToRoom(this.data);
    }
}
