package server;

import server.controller.ConnectionController;
import server.controller.GameRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
    private List<ConnectionController> clients = new ArrayList<>();
    private GameRoom gameRoom = new GameRoom();
    
    public void run() {
        try {
            serverSocket = new ServerSocket(10500);

            while (true) {
                System.out.println("서버 연결 대기중 ....");
                Socket socket = serverSocket.accept();



            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addPlayer(Socket socket) {
        ConnectionController handler = new ConnectionController(socket, this);
        handler.setGameRoom(gameRoom);
        clients.add(handler);
        handler.start();
        System.out.println(socket.getInetAddress().getHostAddress()+"와 연결되었습니다.");
    }

    public void makeGameRoom() {

    }

}
