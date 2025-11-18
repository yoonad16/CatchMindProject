package server.service;

import server.controller.GameRoom;

public class DrawService {
    public void sendDrawDate ( GameRoom gameRoom,String data) {
        gameRoom.broadcastToRoom(data);
    }

}
