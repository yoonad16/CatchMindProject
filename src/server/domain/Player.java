package server.domain;

import server.MessageSender;
import server.controller.ConnectionController;

public class Player {
    private String name;
   private  String id;
    private String password;
    private MessageSender messageSender;
    private int score;

    public Player() {}
    public Player(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public void sendMessage(String msg) {
        messageSender.send(msg);
    }

    //Getter & Setter
    public void setMessageSender(MessageSender messageSender) {this.messageSender = messageSender;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
