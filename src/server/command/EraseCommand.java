package server.command;

import server.controller.GameRoom;
import server.domain.Player;

public class EraseCommand implements Command {
    private String data;

    @Override
    public void create(String data, Player player) {
        this.data = data;
    }
    @Override
    public void execute(GameRoom gameRoom, Player player) {
        gameRoom.broadcastToRoom(this.data);
    }
}
