package client.command;

import client.controller.ViewController;

public class ChatCommand implements Command {
    @Override
    public void execute(ViewController viewController, String data) {
        viewController.updateChatPanel(data);
    }
}