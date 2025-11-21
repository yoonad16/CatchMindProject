package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;

public class StartCommand implements Command {
    @Override
    public void execute(ViewController viewController, String msg) {
        System.out.println("START COMMAND");
        viewController.startGame();
    }
}
