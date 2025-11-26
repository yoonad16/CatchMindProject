package server.service;

import server.controller.GameRoom;
import server.repository.QuizWordRepository;

public class GameWordService {
    public String getNewQuizWord(QuizWordRepository quizWordRepository) {
        String quizWord = quizWordRepository.getRandomWord();
        return quizWord;
    }

    public String changeWord(GameRoom gameRoom, QuizWordRepository quizWordRepository) {
        String nextWord = getNewQuizWord(quizWordRepository);
        gameRoom.setCurrentWord(nextWord);

        System.out.println("[DEBUG] 현재 그림 그리는 사람: " + gameRoom.getDrawer().getName());
        System.out.println("[DEBUG] 선정된 단어: " + nextWord);

        if (gameRoom.getDrawer() != null) {
            gameRoom.getDrawer().sendMessage("KEYWORD:" + nextWord);
            System.out.println("[DEBUG] 서버 -> 클라이언트 전송 완료: KEYWORD:" + nextWord);
        } else {
            System.out.println("[DEBUG] 그림 그리는 사람이 없어서 전송 못함");
        }

        return nextWord;
    }
}
