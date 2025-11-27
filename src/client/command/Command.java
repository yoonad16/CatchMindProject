package client.command;

import client.controller.ViewController;

public interface Command {
    public void execute(ViewController viewController, String msg);
}
