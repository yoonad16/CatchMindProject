package server.service;

import server.controller.ConnectionController;

import java.util.*;

public class WinnerService {

    public List<Map.Entry<ConnectionController,Integer>> getScoreList (List<ConnectionController> players) {
        Map<ConnectionController,Integer> scoreBoard = new HashMap<>();

        for (ConnectionController p : players) {
            scoreBoard.put(p,p.getPlayer().getScore());
        }

        List<Map.Entry<ConnectionController, Integer>> entryList = new ArrayList<>(scoreBoard.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return entryList;
    }
}
