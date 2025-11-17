package server.service;

import server.controller.GameRoom;
import server.domain.Player;

public class GameService {
    private GameRoom gameRoom;

    //DRAW/NAME 아닌 메시지 처리 메소드
    public void answerMessage (String msg, Player player) {
        String message;

        if (compareWord(msg)){
            message = "["+player.getName()+"]: "+msg+"\n";
            message += player.getName()+"님이 정답을 맞추셨습니다.";

            correctAnswer(player);

            gameRoom.broadcastToRoom(message);
        }
        else{
            message = "["+player.getName()+"]: "+msg;
            gameRoom.broadcastToRoom(message);
        }
    }

    //NAME: 닉네임 설정하는 메시지 처리 메소드
    public void nameMessage (String msg, Player player) {
        String message;

        String[] tokens = msg.split(":");
        player.setName(tokens[1]);

        message = player.getName()+"님이 방을 들어오셨습니다.";
        gameRoom.broadcastToRoom(message);
    }

    //답 맞는지 체크하는 메소드
    public boolean compareWord (String word) {
        return word.equalsIgnoreCase(gameRoom.getCurrentWord());
    }

    //📌맞았을 때 로직: 점수 올리기, 제시어 바꾸기, 그림그리는 사람 바꾸기 등
    public void correctAnswer(Player player) {

    }

    //📌제시어 새로 가져오기
    public String getNewQuizWord() {
        String quizWord = null;

        return quizWord;
    }

    //📌그림 그리는 사람 고르는 로직
    public Player selectNextDrawer() {
        Player nextDrawer = null;

        return nextDrawer;
    }

    //getter & setter
    public GameRoom getGameRoom() {return gameRoom;}
    public void setGameRoom(GameRoom gameRoom) {this.gameRoom = gameRoom;}
}
