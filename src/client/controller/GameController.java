package client.controller;

import client.Client;
import client.ClientTest;
import client.command.CommandFactory;

import java.awt.*;

public class GameController {

    private Client client;
    private final CommandFactory commandFactory = CommandFactory.getInstance();
    private ViewController viewController;

    //게임 접속-종료
    public void accessGame(String ip, String name) {
        ClientTest.startGame(ip);
        client.send("NAME:"+name);
    }
    public void noticeStart() {
        client.send("START:");
    }

    public void exitRoom() {
        System.out.println("서버와의 연결이 종료됩니다.");
        client.send("DISCONNECT:");
    }

    public void endConnection() {
        viewController.endPanel();
        client.disconnect();
        System.exit(0);
    }

    //메시지 수신(파싱)
    public void processMessage(String msg) {
        if(msg.startsWith("DISCONNECT")) endConnection();
        commandFactory.createCommand(viewController,msg);
    }

    //메시지 전송
    public void sendChat(String msg) {
        String sendMessage = "CHAT:"+msg;
        client.send(sendMessage);
    }
    public void sendErase() {
        client.send("ERASE:");
    }
    public void sendDrawing(Point from, Point to) {
        String msg = String.format("DRAW:%d:%d:%d:%d", from.x, from.y, to.x, to.y);
        client.send(msg);
    }
    public void sendColor(String colorCode) {client.send("COLOR:"+colorCode);}

    //Setter
    public void setViewController(ViewController viewController) {this.viewController = viewController;}
    public void setClient (Client client) {this.client = client;}
}
