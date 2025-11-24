package client.command;

import client.controller.ViewController;

public class ColorCommand implements Command{
    @Override
    public void execute(ViewController viewController, String data) {
        System.out.println("ColorCommand에서 색 업데이트됨");
        viewController.updateCurrentColor(data);
    }
}
