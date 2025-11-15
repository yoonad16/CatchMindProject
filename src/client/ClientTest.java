package client;

import client.ui.ChatPanel;
import client.ui.MainFrame;

import javax.swing.text.View;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MainFrame mainFrame = new MainFrame();
        Client client = new Client();
        ViewController viewController = new ViewController(client, mainFrame);
        mainFrame.setViewController(viewController);
        client.setViewController(viewController);

        //여기 ip 주소 입력하기
        client.connect("127.0.0.1",50023);
        client.listen();

    }
}
