package server.repository;

public class QuizWordTest {
    public static void main(String[] args) {
        QuizWordRepository repo = new QuizWordRepository();
        String word = repo.getRandomWord();

        if (word != null) {
            System.out.println("랜덤 단어 가져오기 성공: " + word);
        } else {
            System.out.println("랜덤 단어 가져오기 실패");
        }
    }
}
