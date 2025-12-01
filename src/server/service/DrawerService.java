package server.service;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;

import java.sql.Connection;
import java.util.List;

public class DrawerService {

    public ConnectionController selectNextDrawer(List<ConnectionController> players, ConnectionController drawer) {
        if (players.isEmpty()) {
            return null;
        }

        if (drawer == null) {
            return players.get(0);
        }

        int currentIndex = players.indexOf(drawer);

        if (currentIndex == -1)
            return players.get(0);

        return players.get((currentIndex + 1) % players.size());
    }

    public void updatePlayerStates(GameRoom gameRoom, ConnectionController newDrawer) {
        for(ConnectionController c: gameRoom.getPlayers()){
            if(c.equals(newDrawer)) {
                c.getPlayer().setState(new DrawingState());

                c.send("DRAWSTATE:true");
            } else {
                c.getPlayer().setState(new AnsweringState());

                c.send("DRAWSTATE:false");

                c.send("KEYWORD:???");

            }
        }
    }
}
