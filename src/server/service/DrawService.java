package server.service;

import server.controller.GameRoom;

public class DrawService {
    private GameRoom gameRoom;
    public void sendDrawDate (String data) {
        gameRoom.broadcastToRoom(data);
    }

}
