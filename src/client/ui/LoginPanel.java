package client.ui;

import client.ClientTest;
import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginPanel extends JFrame {

    private JButton loginButton;
    private JTextField nameField;
    private JTextField ipField;
    private ViewController viewController;

    public LoginPanel() {
        setSize(800,500);
        setLayout(null);

        loginButton = new JButton("접속하기");
        loginButton.setBounds(570,250,100,50);

        nameField = new JTextField("닉네임 입력하기...");
        nameField.setBounds(250,250,300,50);
        nameField.setForeground(Color.gray);
        ipField = new JTextField();
        ipField.setBounds(250,150,300,50);
        ipField.setForeground(Color.gray);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String name = nameField.getText();
                viewController.accessGame(ip, name);
                accessSuccess();
            }
        });

        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nameField.getText().equals("닉네임 입력하기...")){
                    nameField.setForeground(Color.black);
                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(nameField.getText().equals("닉네임 입력하기...")){
                    nameField.setForeground(Color.gray);
                    nameField.setText("닉네임 입력하기...");
                }

            }
        });

        ipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(ipField.getText().equals("서버 IP주소를 입력하세요")){
                    ipField.setForeground(Color.black);
                    ipField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(ipField.getText().equals("서버 IP주소를 입력하세요")){
                    ipField.setForeground(Color.gray);
                    ipField.setText("서버 IP주소를 입력하세요");
                }
            }
        });

        add(loginButton);
        add(ipField);
        add(nameField);
        setVisible(true);

    }

    public void setViewController(ViewController viewController) {this.viewController = viewController;}
    public void accessSuccess(){this.dispose();}
}
