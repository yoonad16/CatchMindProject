package client.ui;

import client.controller.GameController;
import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends JPanel{

    public JTextArea textArea;
    public JTextField inputText;
    private GameController gameController;

    public ChatPanel() {
        setSize(300,500);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);

        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollBar = new JScrollPane(textArea);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        inputText = new JTextField();


        add(scrollBar, BorderLayout.CENTER);
        add(inputText, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.textArea.append(msg+"\n");
    }
    public void exitRoom() {this.gameController.exitRoom();}

    public void enableChatInput() {inputText.addActionListener(chatting);}
    public void disableChatInput() {inputText.removeActionListener(chatting);}

    ActionListener chatting = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = inputText.getText();
            if (!text.trim().isEmpty()) {
                gameController.sendChat(text);

                inputText.setText("");
            }
        }
    };

    public void setGameController(GameController gameController) {this.gameController = gameController;}
}
