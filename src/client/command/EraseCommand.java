package client.command;

import client.controller.ViewController;

public class EraseCommand implements Command {
    @Override
    public void execute(ViewController viewController, String data) {
        viewController.eraseCanvasPanel();
    }
}
