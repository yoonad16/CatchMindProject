package client.command;

import client.controller.ViewController;

public class TimerCommand implements Command {
    @Override
    public void execute(ViewController viewController, String data) {
        if (data != null) {
            try {
                int time = Integer.parseInt(data);
                viewController.updateTimer(time);
            } catch (NumberFormatException e) {
                System.out.println("타이머 값 파싱 오류: " + data);
            }
        }
    }
}

