package client.command;

import client.controller.ViewController;

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
        commandMap.put("KEYWORD", new KeywordCommand());
        commandMap.put("COLOR", new ColorCommand());
        commandMap.put("TIMER", new TimerCommand());
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public void createCommand(ViewController viewController, String msg) {
        String[] tokens = msg.split(":",2);

        String data;
        if (tokens.length > 1) data = tokens[1];
        else data = null;

        Command commandProcessor = commandMap.get(tokens[0]);

        if (commandProcessor == null) {
            viewController.updateChatPanel(msg);
        }
        else commandProcessor.execute(viewController, data);
    }

}
