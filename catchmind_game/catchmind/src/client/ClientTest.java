package client;

import client.controller.ViewController;
import client.ui.LoginPanel;
public class ClientTest {
    public static void main(String[] args) {

        ViewController viewController = new ViewController();
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setViewController(viewController);

    }
}
