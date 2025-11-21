package client.ui;

import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private JButton leaveButton;
    private JButton startButton;

    private ViewController viewController;

    private JPanel gamePanel;
    public GamePanel() {

        setLayout(new BorderLayout());

        startButton = new JButton("게임 시작하기");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("버튼 눌림");
                viewController.noticeStart();
            }
        });

        leaveButton = new JButton("게임 나가기");
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.exitRoom();
            }
        });

        add(startButton, BorderLayout.NORTH);
        add(leaveButton, BorderLayout.SOUTH);
        setVisible(true);
    }


    public void disableStartButton() {this.startButton.setEnabled(false);}
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
