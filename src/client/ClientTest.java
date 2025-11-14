package client;

import client.ui.ChatPanel;

import javax.swing.text.View;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ChatPanel chatPanel = new ChatPanel();
        Client client = new Client();
        ViewController viewController = new ViewController(client, chatPanel);
        chatPanel.setViewController(viewController);
        client.setViewController(viewController);

        //여기 ip 주소 입력하기
        client.connect("10.240.84.136",50023);
        client.listen();

    }
}
