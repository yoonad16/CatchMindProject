package server.domain;

import server.MessageSender;
import server.repository.PlayerRepository;

public class Player {
    private String name;
   private  int id;
    private int score=0;
    private int win;
    private int playTime;
    private MessageSender messageSender;
    private PlayerState state;


    public Player() {
        // default: 답맞추기
        this.state=new AnsweringState();
    }
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.state=new AnsweringState();
    }

    public Player (String name, int id, int playTime, int win) {
        this.name = name;
        this.win = win;
        this.id = id;
        this.playTime = playTime;
    }


//    public void sendMessage(String msg) {
//        if (messageSender != null) {
//            messageSender.send(msg);
//        } else {
//            System.err.println("[WARNING] " + name + "의 messageSender가 null입니다. 메시지 전송 실패: " + msg);
//        }
//    }

    //Getter & Setter
    public boolean canAnswer() {return this.state.canAnswer();}
    public boolean canDraw() {return this.state.canDraw();}
    public void setMessageSender(MessageSender messageSender) {this.messageSender = messageSender;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void setState(PlayerState newState) {this.state = newState;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}
    public int getWin() {return win;}
    public void setWin(int win) {this.win = win;}
    public int getPlayTime() {return playTime;}
    public void setPlayTime(int playTime) {this.playTime = playTime;}
}
