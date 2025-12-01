package server.command;

import server.controller.ConnectionController;
import server.controller.GameRoom;
import server.domain.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = null;
    Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put("DRAW", new DrawCommand());
        commandMap.put("NAME", new NameCommand());
        commandMap.put("CHAT", new ChatCommand());
        commandMap.put("ERASE", new EraseCommand());
        commandMap.put("START", new StartCommand());
        commandMap.put("COLOR", new ColorCommand());
        commandMap.put("DISCONNECT", new DisconnectCommand());
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public void createCommand(GameRoom gameRoom, String msg, ConnectionController player) {
        String[] tokens = msg.split(":");

        Command commandProcessor = commandMap.get(tokens[0]);
        commandProcessor.create(msg, player);
        commandProcessor.execute(gameRoom, player);
    }
}
