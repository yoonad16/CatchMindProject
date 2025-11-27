package server.command;

import server.controller.GameController;
import server.domain.Player;

public class DrawCommand implements Command {
    private String message;

    @Override
    public void create(String data, Player player) {
        this.message = data;
    }

    @Override
    public void execute(GameController gameController, Player player) {
       gameController.broadcastToRoom(this.message);
    }
}
