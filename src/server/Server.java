package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
	
	private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    
    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, this);
            clients.add(handler);
            handler.start();
        }
    }

    public void broadcast(String msg) {
        for (ClientHandler c : clients) c.send(msg);
    }

}
