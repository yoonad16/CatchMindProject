package server.service;

import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;

public class StateService {

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
