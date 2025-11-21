package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;

public interface Command {
    public void execute(ViewController viewController, String msg);
}
