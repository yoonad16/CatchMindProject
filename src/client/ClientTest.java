package client;

import client.controller.ViewController;
import client.ui.LoginPanel;
import client.ui.MainFrame;

import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ViewController viewController = new ViewController();
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setViewController(viewController);

    }
}
