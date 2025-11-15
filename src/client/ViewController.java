package client;

import client.ui.ChatPanel;
import client.ui.MainFrame;

import java.awt.*;

public class ViewController {
    private Client client;
    private MainFrame mainFrame;

    public ViewController(Client client, MainFrame mainFrame) {
        this.client = client;
        this.mainFrame = mainFrame;
    }

    public void updateChatPanel(String msg) {
        mainFrame.updateTextArea(msg);
    }

    public void sendChat(String msg) {
        client.send(msg);
    }

    public void sendCanvas(Point from, Point to) {
        client.sendDrawing(from, to);
    }

    public void updateCanvasPanel(Point from, Point to) {
        mainFrame.updateCanvas(from, to);
    }
}
