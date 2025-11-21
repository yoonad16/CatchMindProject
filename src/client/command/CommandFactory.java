package client.command;

import client.controller.ViewController;
import server.controller.GameRoom;
import server.domain.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = null;
    Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put("DRAW", new DrawCommand());
        commandMap.put("CHAT", new ChatCommand());
        commandMap.put("ERASE", new EraseCommand());
        commandMap.put("START", new StartCommand());
        commandMap.put("DRAWSTATE", new DrawStateCommand());
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public void createCommand(ViewController viewController, String msg) {
        String[] tokens = msg.split(":");

        Command commandProcessor = commandMap.get(tokens[0]);
        commandProcessor.execute(viewController, msg);
    }

}
