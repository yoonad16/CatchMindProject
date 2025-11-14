package client;

import client.ui.ChatPanel;

public class ViewController {
    private Client client;
    private ChatPanel chatPanel;

    public ViewController(Client client, ChatPanel chatPanel) {
        this.client = client;
        this.chatPanel = chatPanel;
    }

    public void updateChatPanel(String msg) {
        chatPanel.updateTextArea(msg);
    }

    public void sendChat(String msg) {
        client.send(msg);
    }
}
