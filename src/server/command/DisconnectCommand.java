package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;

public class DisconnectCommand implements Command {
    private String message;
    @Override
    public void create(String data, ConnectionController player) {this.message = data;}

    @Override
    public void execute(GameRoom gameRoom, ConnectionController player) {
        System.out.println("여기 디스커넷트");
        gameRoom.removePlayer(player);}


}
