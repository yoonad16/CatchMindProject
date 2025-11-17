package server.controller;

import server.Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

//컨트롤러: 통신/송수신 담당
public class ClientController extends Thread {
    public Socket socket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private GameRoom gameRoom;
    private String name;

    public ClientController(Socket socket, Server server) {
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
    }

    //클라이언트가 보낸게 이쪽의 in으로 들어와서, 브로드캐스트(다른 클라이언트들)한테 보내짐
    public void run() {
        String msg;
            try {
                while ((msg=in.readLine()) != null) {
                    gameRoom.processMessage(this, msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //서버의 브로드캐스트 메소드에서 이 메소드 호출돼서 각 클라이언트들한테 재전송됨
    public void send(String msg) {
        System.out.println(msg);
        out.println(msg);
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public String getPlayerName(){
        return this.name;
    }
}
