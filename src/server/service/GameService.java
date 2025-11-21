package server.service;

import server.controller.GameRoom;
import server.domain.AnsweringState;
import server.domain.DrawingState;
import server.domain.Player;
import server.repository.QuizWordRepository;

import java.util.List;
import java.util.Map;

public class GameService {
    public void startGame(GameRoom gameRoom) {
        gameRoom.broadcastToRoom("[System] 게임이 시작되었습니다!");
        nextRound(gameRoom);
    }

    // 정답 당 10점씩 올라가는걸로 일단 구현할게요
    private static final int SCORE_PER_ANSWER = 10;
    private final QuizWordRepository quizWordRepository;

    public GameService(QuizWordRepository quizWordRepository) {
        this.quizWordRepository = quizWordRepository;
    }

    // 점수 관리

    // 정답 맞춘 플레이어에게 점수 주기
    public void addScore(GameRoom gameRoom, Player player) {
        Map<Player, Integer> board = gameRoom.getScoreBoard();
        Integer currentScore = board.get(player);

        if (currentScore == null)
            currentScore = 0;

        board.put(player, board.get(player) + SCORE_PER_ANSWER);
    }

    public int getPlayerScore(GameRoom gameRoom, Player player) {
        Integer score = gameRoom.getScoreBoard().get(player);
        if (score == null) {
            return 0;
        }
        return score;

    }

    //답 맞는지 체크하는 메소드
    public boolean compareWord (GameRoom gameRoom,String word) {
        String correctWord = gameRoom.getCurrentWord();
        if(correctWord == null || word == null)
            return false;
        return word.equalsIgnoreCase(correctWord);
    }

    // 맞았을 때 로직: 점수 올리기, 제시어 바꾸기, 그림그리는 사람 바꾸기 등.. => SRP 위반 => 기능별로 메소드 분리함
    public void correctAnswer(Player player, GameRoom gameRoom, String msg) {

        gameRoom.broadcastToRoom("[" + player.getName() + "]: " + msg);
        gameRoom.broadcastToRoom("[System] " + player.getName() + "님이 정답을 맞추셨습니다! (+" + SCORE_PER_ANSWER + "점)");

        // 점수 추가
        addScore(gameRoom, player);

        // 다음 라운드 진행
        nextRound(gameRoom);
    }
    // 다음 라운드 준비
    private void nextRound(GameRoom gameRoom) {
        // 다음 그림 그리는 사람 선택
        Player newDrawer = gameRoom.selectNextDrawer();
        gameRoom.setDrawer(newDrawer);

        // 사용자 업데이트
        updatePlayerStates(gameRoom, newDrawer);

        // 제시어 변경
        String newWord = changeWord(gameRoom);

        for (Player p : gameRoom.getPlayers()) {
            if (!p.equals(gameRoom.getDrawer()))
                p.sendMessage("[System] 새로운 라운드가 시작되었습니다!");
        }
        gameRoom.broadcastToRoom("다음 그림 그리는 사람은 "+newDrawer.getName()+"님 입니다.");

    }
    // 제시어 바꾸기
    private String changeWord(GameRoom gameRoom) {
        String nextWord = getNewQuizWord();
        gameRoom.setCurrentWord(nextWord);

        System.out.println("[DEBUG] 현재 술래: " + gameRoom.getDrawer().getName());
        System.out.println("[DEBUG] 선정된 단어: " + nextWord);

        if (gameRoom.getDrawer() != null) {
            gameRoom.getDrawer().sendMessage("KEYWORD:" + nextWord);
            System.out.println("[DEBUG] 서버 -> 클라이언트 전송 완료: KEYWORD:" + nextWord);
        } else {
            System.out.println("[DEBUG] 술래가 null이라서 전송 못함!");
        }

        return nextWord;
    }

    // 사용자 상태 업데이트
    public void updatePlayerStates(GameRoom gameRoom, Player newDrawer) {
        for(Player p: gameRoom.getPlayers()){
            if(p.equals(newDrawer))
                p.setState(new DrawingState());

            else
                p.setState(new AnsweringState());
        }
    }

    // 제시어 새로 가져오기
    public String getNewQuizWord() {
        String quizWord = quizWordRepository.getRandomWord();
        return quizWord;
    }
}