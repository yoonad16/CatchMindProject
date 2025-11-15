package server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//접속하는 클라이언트마다 이 클래스 생성됨
public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Server server) {
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
                    System.out.println(msg);
                    if(msg.charAt(0) != '$'){
                        msg = "["+socket.getInetAddress().getHostAddress()+"]: "+msg;
                    }
                    server.broadcast(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //서버의 브로드캐스트 메소드에서 이 메소드 호출돼서 각 클라이언트들한테 재전송됨
    public void send(String msg) {
        out.println(msg);
    }
}
