package server;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.service.DrawService;
import server.service.GameService;
import server.repository.QuizWordRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
    private final List<ConnectionController> clients = new ArrayList<>();
    private final List<GameRoom> gameRooms = new ArrayList<>();
    private DrawService drawService;
    private GameService gameService;
    private QuizWordRepository quizWordRepository;

    public Server(){
        this.drawService = new DrawService();
        this.quizWordRepository = new QuizWordRepository();
        this.gameService = new GameService(this.quizWordRepository);
    }

    public void run() {
        makeGameRoom();
        try {
            serverSocket = new ServerSocket(50023);

            while (true) {
                System.out.println("서버 연결 대기중 ....");
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress()+"와 연결되었습니다.");
                addPlayer(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addPlayer(Socket socket) {
        GameRoom gameRoom = gameRooms.get(0);

        ConnectionController handler = new ConnectionController(socket, this);
        handler.setGameRoom(gameRoom);
        clients.add(handler);
        handler.start();
    }

    public void makeGameRoom() {
        GameRoom newGameRoom = new GameRoom(drawService, gameService);
        this.gameRooms.add(newGameRoom);
    }

}
