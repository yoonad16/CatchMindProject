package client.test;

import client.Client;
import client.controller.GameController;
import client.controller.ViewController;
import client.ui.LoginPanel;
import client.ui.MainFrame;

public class ClientTest {
    private static ViewController viewController;
    private static GameController gameController;
    public static void main(String[] args) {

        viewController = new ViewController();
        gameController = new GameController();
        gameController.setViewController(viewController);
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setGameController(gameController);

    }

    public static void startGame(String ip) {
        Client client = new Client();
        client.connect(ip,50023);
        client.listen();

        client.setGameController(gameController);
        gameController.setClient(client);

        MainFrame mainFrame = new MainFrame();
        viewController.setMainFrame(mainFrame);
        mainFrame.setGameController(gameController);
    }
}
