package server.command;

import server.controller.GameRoom;
import server.domain.Player;
import server.service.DrawService;

public class DrawCommand implements Command {
    private String message;

    @Override
    public void create(String data, Player player) {
        this.message = data;
    }

    @Override
    public void execute(GameRoom gameRoom, Player player) {
       gameRoom.broadcastToRoom(this.message);
    }
}
