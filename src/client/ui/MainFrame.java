package client.ui;

import client.controller.ViewController;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class MainFrame extends JFrame {

    private ChatPanel chatPanel;
    private CanvasPanel canvasPanel;
    private JLabel timerLabel;
    private Timer countdownTimer;
    private TimerTask countdownTask;
    private int remainingSeconds;

    public MainFrame() {
        setSize(800,500);
        setLayout(new BorderLayout());
        chatPanel = new ChatPanel();
        canvasPanel = new CanvasPanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,500));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.lightGray);

        // 타이머 레이블 추가
        timerLabel = new JLabel("시간: --");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(Color.RED);
        timerLabel.setPreferredSize(new Dimension(200, 30));

        rightPanel.add(chatPanel, BorderLayout.CENTER);
        rightPanel.add(timerLabel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        add(canvasPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateTextArea(String msg) {
        this.chatPanel.updateTextArea(msg);
    }

    public void setViewController (ViewController viewController) {
        chatPanel.setViewController(viewController);
        canvasPanel.setViewController(viewController);
    }

    public void updateCanvas(Point from, Point to) {
        canvasPanel.paintCanvas(from, to);
    }

    public void eraseCanvas() {
        canvasPanel.eraseCanvas();
    }

    public void disableDrawing() {canvasPanel.disableCanvasDrawing();}
    public void enableDrawing() {canvasPanel.enalbeCanvasDrawing();}
    public void updateKeyWord(String word) {
        this.canvasPanel.setKeyword(word);
    }

    public void startTimer(int timeLimit) {
        stopTimer(); // 기존 타이머가 있으면 중지
        
        remainingSeconds = timeLimit;
        timerLabel.setText("시간: " + remainingSeconds + "초");
        timerLabel.setVisible(true);
        
        countdownTimer = new Timer();
        countdownTask = new TimerTask() {
            @Override
            public void run() {
                remainingSeconds--;
                SwingUtilities.invokeLater(() -> {
                    if (remainingSeconds > 0) {
                        timerLabel.setText("시간: " + remainingSeconds + "초");
                        // 10초 이하일 때 색상 변경
                        if (remainingSeconds <= 10) {
                            timerLabel.setForeground(Color.RED);
                        } else {
                            timerLabel.setForeground(Color.BLACK);
                        }
                    } else {
                        timerLabel.setText("시간: 0초");
                        timerLabel.setForeground(Color.RED);
                        stopTimer();
                    }
                });
            }
        };
        
        countdownTimer.scheduleAtFixedRate(countdownTask, 1000, 1000); // 1초마다 실행
    }

    public void stopTimer() {
        if (countdownTimer != null) {
            countdownTimer.cancel();
            countdownTimer.purge();
            countdownTimer = null;
        }
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
        }
        timerLabel.setText("시간: --");
        timerLabel.setForeground(Color.BLACK);
    }
}
