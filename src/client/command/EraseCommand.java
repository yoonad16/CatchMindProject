package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;

public class EraseCommand implements Command {
    @Override
    public void execute(ViewController viewController, String msg) {
        viewController.eraseCanvasPanel();
    }
}
