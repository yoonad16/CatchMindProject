package client.command;

import client.controller.ViewController;

public class DrawStateCommand implements Command{

    @Override
    public void execute(ViewController viewController, String msg) {
        String[] tokens = msg.split(":");
        boolean drawState;

        if (tokens[1].equals("true")) drawState = true;
        else drawState = false;

        viewController.updateDrawState(drawState);
    }
}
