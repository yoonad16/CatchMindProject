package server.service;

import server.controller.ConnectionController;
import server.domain.Player;
import server.repository.PlayerRepository;

import java.util.List;
import java.util.Map;

public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findPlayer(String name) {
        return playerRepository.findPlayerByName(name);
    }

    public Player addNewPlayer(String name) {
        Player p = new Player();
        p.setName(name);
        playerRepository.savePlayer(p);

        return p;
    }

    public void updatePlayerStatus (Player player) {
        playerRepository.updatePlayer(player);
    }
}
