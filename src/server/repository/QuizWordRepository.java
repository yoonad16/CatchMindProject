package server.repository;

import server.domain.QuizWord;

import java.util.ArrayList;
import java.util.List;

public class QuizWordRepository {

    //여기서 랜덤한 제시어 하나 받아갈 수 있다고 생각하고 메소드 갖다 쓰면 될듯합니당
    public QuizWord getRandQuizWord() {
        QuizWord quizWord = new QuizWord(1,"산","지형");

        return quizWord;
    }
}
