package client.command;

import client.controller.ViewController;

public class TimerCommand implements Command {
    @Override
    public void execute(ViewController viewController, String data) {
        if (data == null) return;
        
        String[] tokens = data.split(":");
        if (tokens.length < 1) return;
        
        String action = tokens[0];
        
        if (action.equals("START") && tokens.length >= 2) {
            int timeLimit = Integer.parseInt(tokens[1]);
            viewController.startTimer(timeLimit);
        } else if (action.equals("STOP")) {
            viewController.stopTimer();
        }
    }
}

