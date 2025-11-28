package server.service;

import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;

import java.util.List;

public class DrawerService {

    public Player selectNextDrawer(List<Player> players, Player drawer) {
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

    public void updatePlayerStates(GameRoom gameRoom, Player newDrawer) {
        for(Player p: gameRoom.getPlayers()){
            if(p.equals(newDrawer)) {
                p.setState(new DrawingState());

                p.sendMessage("DRAWSTATE:true");
            } else {
                p.setState(new AnsweringState());

                p.sendMessage("DRAWSTATE:false");

                p.sendMessage("KEYWORD:???");

            }
        }
    }
}
