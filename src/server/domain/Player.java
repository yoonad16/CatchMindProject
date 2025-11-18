package server.domain;

import server.MessageSender;

public class Player {
    private String name;
   private  String id;
    private String password;
    private MessageSender messageSender;
    private PlayerState state;
    private int score;

    public Player() {
        // default: 답맞추기
        this.state=new AnsweringState();
    }
    public Player(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.state=new AnsweringState();
    }

    public void sendMessage(String msg) {
        messageSender.send(msg);
    }

    //Getter & Setter
    public void setMessageSender(MessageSender messageSender) {this.messageSender = messageSender;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void setState(PlayerState newState) {this.state = newState;}
    public boolean canDraw() {return state.canDraw();}
    public boolean canAnswer(){return state.canAnswer();}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
