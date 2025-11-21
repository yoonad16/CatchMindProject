package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;

import java.awt.*;

public class DrawCommand implements Command {
    @Override
    public void execute(ViewController viewController, String msg) {
        String[] tokens = msg.split(":");

        Point from = new Point(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
        Point to = new Point(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));

        viewController.updateCanvasPanel(from, to);
    }
}
