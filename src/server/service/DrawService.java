package server.service;

import server.controller.GameRoom;

public class DrawService {
    public void sendDrawData(GameRoom gameRoom, String data) {
        gameRoom.broadcastToRoom(data);
    }

}
