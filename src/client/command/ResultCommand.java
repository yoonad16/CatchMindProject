package client.command;

import client.controller.ViewController;

public class ResultCommand implements Command {

    @Override
    public void execute(ViewController viewController, String msg) {
        System.out.println("여기 받음");
        viewController.printResult(msg);
    }
}
