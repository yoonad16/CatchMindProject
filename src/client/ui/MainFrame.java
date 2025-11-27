package client.ui;

import client.controller.GameController;
import client.controller.ViewController;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private ChatPanel chatPanel;
    private CanvasPanel canvasPanel;
    private GamePanel gamePanel;

    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        setSize(800,500);
        setLayout(new BorderLayout());

        chatPanel = new ChatPanel();
        chatPanel.setPreferredSize(new Dimension(200,450));
        chatPanel.setEnabled(false);

        canvasPanel = new CanvasPanel();
        canvasPanel.setEnabled(false);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(200,50));


        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,500));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.lightGray);



        rightPanel.add(chatPanel, BorderLayout.CENTER);
        rightPanel.add(gamePanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
        add(canvasPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.chatPanel.updateTextArea(msg);
    }

    public void setGameController (GameController gameController) {
        canvasPanel.setGameController(gameController);
        gamePanel.setGameController(gameController);
    }

    public void enablePanel() {
        this.canvasPanel.enalbeCanvasDrawing();
        this.chatPanel.enableChatInput();
    }
    public void updateCanvas(Point from, Point to) {
        canvasPanel.paintCanvas(from, to);
    }
    public void updateCurrentColor (String msg) {this.canvasPanel.updateColor(msg);}
    public void eraseCanvas() {
        canvasPanel.eraseCanvas();
    }
    public void disableStartButton() {gamePanel.disableStartButton();}
    public void disableDrawing() {canvasPanel.disableCanvasDrawing();}
    public void enableDrawing() {canvasPanel.enalbeCanvasDrawing();}
    public void disableChatting() {chatPanel.disableChatInput();}
    public void enableChatting() {chatPanel.enableChatInput();}
    public void updateKeyWord(String word) {
        this.canvasPanel.setKeyword(word);
    }
    
    public void updateTimer(int time) {
        this.canvasPanel.updateTimer(time);
    }
}
