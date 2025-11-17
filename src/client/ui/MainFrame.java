package client.ui;

import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ChatPanel chatPanel;
    private CanvasPanel canvasPanel;

    public MainFrame() {
        setSize(800,500);
        setLayout(new BorderLayout());
        chatPanel = new ChatPanel();
        canvasPanel = new CanvasPanel();

        chatPanel.setPreferredSize(new Dimension(200,0));
        add(chatPanel, BorderLayout.EAST);
        add(canvasPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.chatPanel.updateTextArea(msg);
    }

    public void setViewController (ViewController viewController) {
        chatPanel.setViewController(viewController);
        canvasPanel.setViewController(viewController);
    }

    public void updateCanvas(Point from, Point to) {
        canvasPanel.paintCanvas(from, to);
    }
}
