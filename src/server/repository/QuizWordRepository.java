package server.repository;

import server.domain.QuizWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuizWordRepository {
    private final List<String> wordList = Arrays.asList(
            "사과", "바나나", "자동차", "비행기", "컴퓨터",
            "축구", "피아노", "고양이", "강아지", "아이스크림",
            "우산", "시계", "안경", "모자", "자전거"
    );

    public String getRandomWord(){
        int randomIndex = new Random().nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
}
