package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;

public class ColorCommand implements Command {
    String message;

    @Override
    public void execute(GameRoom gameRoom, ConnectionController player) {
        gameRoom.broadcastToRoom(this.message);
    }

    @Override
    public void create(String data, ConnectionController player) {
        this.message = data;
    }
}
