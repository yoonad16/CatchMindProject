package server;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.repository.PlayerRepository;
import server.service.*;
import server.repository.QuizWordRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
    private final List<GameRoom> gameRooms = new ArrayList<>();
    private GameService gameService;
    private CheckAnswerService checkAnswerService;
    private DrawerService drawerService;
    private GameWordService gameWordService;
    private QuizWordRepository quizWordRepository;
    private PlayerRepository playerRepository;
    private PlayerService playerService;
    private WinnerService winnerService;

    public Server(){
        this.quizWordRepository = new QuizWordRepository();
        this.playerRepository = new PlayerRepository();

        this.checkAnswerService = new CheckAnswerService();
        this.drawerService = new DrawerService();
        this.gameWordService = new GameWordService();
        this.winnerService = new WinnerService();
        this.playerService = new PlayerService(this.playerRepository);

        this.gameService = new GameService(this.drawerService, this.gameWordService,
                this.checkAnswerService, this.winnerService, this.quizWordRepository);
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

        ConnectionController handler = new ConnectionController(socket, this, this.playerService);
        System.out.println("ConnectionController에 playerServcie 넣음");
        handler.setGameRoom(gameRoom);
        handler.start();
    }

    public void makeGameRoom() {
        GameRoom newGameRoom = new GameRoom(gameService);
        this.gameRooms.add(newGameRoom);
    }

}
