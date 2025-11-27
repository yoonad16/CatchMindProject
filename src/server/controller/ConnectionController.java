package server.controller;

import server.MessageSender;
import server.Server;
import server.domain.Player;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

//컨트롤러: 통신/송수신 담당
public class ConnectionController extends Thread implements MessageSender {
    public Socket socket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private GameController gameController;
    private Player player;


    //생성자: 입출력 설정 | 플레이어 만들기 (컨트롤러랑 1:1 대응이라..?) + 플레이어 게임룸에 추가해주기
    public ConnectionController(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            in = new BufferedReader(input);
            OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8);
            out = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //플레이어 설정해주기
        this.player = new Player();
        player.setMessageSender(this);
    }

    //클라이언트가 보낸게 이쪽의 in으로 들어와서, 브로드캐스트(다른 클라이언트들)한테 보내짐
    public void run() {
        String msg;
            try {
                while ((msg=in.readLine()) != null) {
                    gameController.processMessage(this.player, msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(player.getName()+"님의 연결이 종료되었습니다.");
            } finally {
                if (gameController != null) {
                    gameController.removePlayer(this.player);
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    //서버의 브로드캐스트 메소드에서 이 메소드 호출돼서 각 클라이언트들한테 재전송됨
    public void send(String msg) {
        System.out.println(msg);
        out.println(msg);
    }

    //getter&setter
    public void setGameRoom(GameController gameController) {
        this.gameController = gameController;
        this.gameController.addPlayer(player);
    }
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
}
