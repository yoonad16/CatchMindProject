package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;

public class EraseCommand implements Command {
    private String data;

    @Override
    public void create(String data, ConnectionController player) {
        this.data = data;
    }
    @Override
    public void execute(GameRoom gameRoom, ConnectionController player) {
        gameRoom.broadcastToRoom(this.data);
    }
}
