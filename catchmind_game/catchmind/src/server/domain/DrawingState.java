package server.domain;

public class DrawingState implements PlayerState {
    @Override
    public boolean canDraw() { return true; }

    @Override
    public boolean canAnswer() { return false; } // 화가는 정답 못맞춤
}