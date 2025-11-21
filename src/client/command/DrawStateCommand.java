package client.command;

import client.controller.ViewController;

public class DrawStateCommand implements Command{

    @Override
    public void execute(ViewController viewController, String data) {
        boolean drawState;

        if (data.equals("true")) drawState = true;
        else drawState = false;

        viewController.updateDrawState(drawState);
    }
}
