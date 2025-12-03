package client.command;

import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;

public class DrawCommand implements Command {
    @Override
    public void execute(ViewController viewController, String msg) {
        String[] tokens = msg.split(":");

        Point from = new Point(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
        Point to = new Point(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                viewController.updateCanvasPanel(from, to);
            }
        });
    }
}
