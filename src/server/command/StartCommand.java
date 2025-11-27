package server.command;

import server.controller.GameController;
import server.domain.Player;

public class StartCommand implements Command {

    String data;

    @Override
    public void execute(GameController gameController, Player player) {
        gameController.broadcastToRoom(data);
        gameController.getGameService().startGame(gameController);
    }

    @Override
    public void create(String data, Player player) {
        this.data = data;
    }
}
