package server.command;

import server.controller.GameRoom;
import server.domain.Player;

public class NameCommand implements Command {

    private String data = "NAME:";

    @Override
    public void create(String data, Player player) {
        System.out.println("----------");
        String[] tokens = data.split(":");
        this.data = tokens[1];
    }

    @Override
    public void execute(GameRoom gameRoom, Player player) {
        player.setName(this.data);
        gameRoom.broadcastToRoom("CHAT:["+this.data+"]"+"님이 입장하셨습니다.");
    }
}
