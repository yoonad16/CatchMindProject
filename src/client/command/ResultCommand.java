package client.command;

import client.controller.ViewController;

public class ResultCommand implements Command {

    @Override
    public void execute(ViewController viewController, String msg) {
        viewController.printResult(msg);
    }
}
