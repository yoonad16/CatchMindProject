package client.ui;

import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends JPanel{

    public JTextArea textArea;
    public JTextField inputText;

    private ViewController viewController;

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

        inputText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    String msg = inputText.getText();
                    viewController.sendChat(msg);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    inputText.setText("");
                }
            }
        });


        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.textArea.append(msg+"\n");
    }

    public void setViewController (ViewController viewController) {
        this.viewController = viewController;
    }

}
