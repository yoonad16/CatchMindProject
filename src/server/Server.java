package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    
    public void run() {
        try {
            serverSocket = new ServerSocket(50023);

            while (true) {
                System.out.println("서버 연결 대기중 ....");
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);
                clients.add(handler);
                handler.start();
                System.out.println(socket.getInetAddress().getHostAddress()+"와 연결되었습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void broadcast(String msg) {
        for (ClientHandler c : clients) {
            c.send(msg);
        }
    }

}
