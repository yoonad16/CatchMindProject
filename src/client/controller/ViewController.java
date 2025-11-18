package client.controller;

import client.Client;
import client.ui.MainFrame;

import java.awt.*;

import static java.lang.System.exit;

//중계자: 클라이언트-UI 연결하고 중계
public class ViewController {
    private Client client;
    private MainFrame mainFrame;


    public void sendChat(String msg) {
        client.send(msg);
    }
    public void sendErase(String msg) {client.send(msg);}
    public void sendDrawing(Point from, Point to) {
        client.sendDrawing(from, to);
    }

    public void updateCanvasPanel(Point from, Point to) {mainFrame.updateCanvas(from, to);}
    public void eraseCanvasPanel() {mainFrame.eraseCanvas();}
    public void updateChatPanel(String msg) {
        mainFrame.updateTextArea(msg);
    }

    //여기서 객체 생성 하지 말고 DI 의존성 주입 하기 (메인 클래스의 메소드에서 생성해서 세팅하기)
    public void accessGame(String ip, String name) {
        this.mainFrame = new MainFrame();
        this.mainFrame.setViewController(this);

        client = new Client();
        client.connect(ip,50023);
        client.send("NAME:"+name);
        client.listen();
        client.setViewController(this);
    }

    public void exitRoom() {
        mainFrame.dispose();
        System.out.println("서버와의 연결이 종료됩니다.");
        client.disconnect();
        System.exit(0);
    }
}
