package server.command;

import server.controller.GameRoom;
import server.domain.Player;

public class StartCommand implements Command {

    String data;

    @Override
    public void execute(GameRoom gameRoom, Player player) {
        gameRoom.broadcastToRoom(data);
        gameRoom.getGameService().startGame(gameRoom);
    }

    @Override
    public void create(String data, Player player) {
        this.data = data;
    }
}
