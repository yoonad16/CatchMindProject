package server.command;

import server.controller.GameRoom;
import server.domain.Player;

public class ColorCommand implements Command {
    String message;

    @Override
    public void execute(GameRoom gameRoom, Player player) {
        gameRoom.broadcastToRoom(this.message);
    }

    @Override
    public void create(String data, Player player) {
        this.message = data;
    }
}
