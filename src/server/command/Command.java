package server.command;

import server.controller.GameController;
import server.domain.Player;

public interface Command {
    public void execute(GameController gameController, Player player);
    public void create(String data, Player player);
}
