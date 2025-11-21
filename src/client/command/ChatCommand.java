package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;
import server.service.GameService;

public class ChatCommand implements Command {
    @Override
    public void execute(ViewController viewController, String msg) {
        String[] tokens = msg.split(":");
        viewController.updateChatPanel(tokens[1]);
    }
}