package server.service;

import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;
import server.repository.QuizWordRepository;

import java.util.List;

public class GameService {
    private final QuizWordRepository quizWordRepository;
    public GameService(QuizWordRepository quizWordRepository) {
        this.quizWordRepository = quizWordRepository;
    }

    //DRAW/NAME 아닌 메시지 처리 메소드
    public void answerMessage (GameRoom gameRoom,String msg, Player player) {
        String message;

        // 그림그리는 사람이 채팅한 경우, 일반 채팅으로 처리
        if(!player.canAnswer()) {
            message = "[" + player.getName() + "]: " + msg;
            gameRoom.broadcastToRoom(message);
            return;
        }

        if (compareWord(gameRoom, msg)){
            message = "["+player.getName()+"]: "+msg+"\n";
            message += player.getName()+"님이 정답을 맞추셨습니다.";

            correctAnswer(player, gameRoom);
            gameRoom.broadcastToRoom(message);
        }
        else{
            message = "["+player.getName()+"]: "+msg;
            gameRoom.broadcastToRoom(message);
        }
    }

    //NAME: 닉네임 설정하는 메시지 처리 메소드
    public void nameMessage (GameRoom gameRoom, String msg, Player player) {
        String message;

        String[] tokens = msg.split(":");
        player.setName(tokens[1]);

        message = player.getName()+"님이 방을 들어오셨습니다.";
        gameRoom.broadcastToRoom(message);
    }

    //답 맞는지 체크하는 메소드
    public boolean compareWord (GameRoom gameRoom,String word) {
        String correctWord = gameRoom.getCurrentWord();
        if(correctWord == null || word == null){
            return false;
        }
        return word.equalsIgnoreCase(gameRoom.getCurrentWord());
    }

    //📌맞았을 때 로직: 점수 올리기, 제시어 바꾸기, 그림그리는 사람 바꾸기 등
    public void correctAnswer(Player player, GameRoom gameRoom) {
        // 아직 점수구현 안함

        // 다음 화가 선택
        Player newDrawer = selectNextDrawer(gameRoom);
        if(newDrawer == null){
            return;
        }
        gameRoom.setDrawer(newDrawer);


        // 사용자 상태 업데이트
        for(Player p: gameRoom.getPlayers()){
            if(p.equals(newDrawer))
                p.setState(new DrawingState());

            else
                p.setState(new AnsweringState());
        }
    }

    //📌제시어 새로 가져오기
    public String getNewQuizWord() {
        String quizWord = null;

        return quizWord;
    }

    public Player selectNextDrawer(GameRoom gameRoom) {

        // GameRoom에서 플레이어 리스트 가져옴
        List<Player> players = gameRoom.getPlayers();
        if(players == null || players.isEmpty())
            return null; // 방이 비었으면 null
        
        // 지금 그림 그리는 사람
        Player currentPlayer = gameRoom.getDrawer();
        int currentIndex = players.indexOf(currentPlayer);

        // 예외 처리: 그림 그리는 사람 없거나 중간에 퇴장했으면 첫번째 사람으로 ,,
        if(currentIndex == -1)
            return players.get(0);
        // 다음 그림 그리는 사람
        else{
            int nextIndex = (currentIndex + 1) % players.size();
            return players.get(nextIndex);

        }
    }
}
