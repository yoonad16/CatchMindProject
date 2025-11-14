package client.ui;

import client.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends JFrame {

    public JTextArea textArea;
    public JTextField inputText;

    private ViewController viewController;

    public static void main(String[] args) {
        new ChatPanel();
    }
    public ChatPanel() {
        setSize(800,500);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        inputText = new JTextField();


        add(textArea, BorderLayout.NORTH);
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
                    inputText.setText(" ");
                }
            }
        });


        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.textArea.append(msg+"\n");
//       SwingUtilities.invokeLater(()->{
//           this.textArea.append(msg+"\n");
//       });
    }

    public void setViewController (ViewController viewController) {
        this.viewController = viewController;
    }

}
