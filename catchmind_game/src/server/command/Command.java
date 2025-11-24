package server.command;

import server.controller.GameRoom;
import server.domain.Player;

public interface Command {
    public void execute(GameRoom gameRoom, Player player);
    public void create(String data, Player player);
}
