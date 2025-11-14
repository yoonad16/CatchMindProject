package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//접속하는 클라이언트마다 이 클래스 생성됨
public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private Scanner in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //클라이언트가 보낸게 이쪽의 in으로 들어와서, 브로드캐스트(다른 클라이언트들)한테 보내짐
    public void run() {
        String msg;
        while (in.hasNext()) {
            msg = in.nextLine();
            server.broadcast(msg);
        }
    }

    //서버의 브로드캐스트 메소드에서 이 메소드 호출돼서 각 클라이언트들한테 재전송됨
    public void send(String msg) {
        out.println(msg);
    }
}
