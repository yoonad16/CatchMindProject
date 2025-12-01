package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;

public interface Command {
    public void execute(GameRoom gameRoom, ConnectionController player);
    public void create(String data, ConnectionController player);
}
