package server.domain;

public class   QuizWord {
    //퀴즈단어 객체 필드 참고 (아마 word정도 쓸 듯..?)
    private int id;
    private String word;
    private String category;

    public QuizWord (int id, String word, String category) {
        this.id = id;
        this.word = word;
        this.category = category;
    }

    public String getWord() {return this.word;}
    public int getId() {return this.id;}
    public String getCategory() {return this.category;}
}
