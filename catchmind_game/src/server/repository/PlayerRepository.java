package server.repository;

import server.domain.Player;

public class PlayerRepository {

    public Player findPlayerByName(String name) {
        String id = "테스트용 ID";
        String password = "테스트용 PW";
        Player player = new Player(name ,id, password);

        return player;
    }

    public void savePlayer(Player player) {
    	
    }

}
