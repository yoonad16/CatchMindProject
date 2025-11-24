package client.command;

import client.controller.ViewController;

public class ColorCommand implements Command{
    @Override
    public void execute(ViewController viewController, String data) {
        viewController.updateCurrentColor(data);
    }
}
