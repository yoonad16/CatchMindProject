package server.domain;

public class AnsweringState implements PlayerState {
    @Override
    public boolean canDraw() { return false; } // 답 맞추는 사람은 그림 못그림

    @Override
    public boolean canAnswer() { return true; }
}