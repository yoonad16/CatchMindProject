package client.command;

import client.controller.ViewController;

public class KeywordCommand implements Command {

    @Override
    public void execute(ViewController viewController, String data) {
        viewController.updateKeyWord(data);
    }
}
