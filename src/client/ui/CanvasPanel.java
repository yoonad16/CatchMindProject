package client.ui;

import client.controller.GameController;
import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasPanel extends JPanel {

    private JPanel canvas;
    private ColorPalette colorPalette;
    private JTextField keyword;
    private JLabel timerLabel;
    private Point lastPoint = null;
    private GameController gameController;
    private JButton eraseButton;
    private Color currentColor = Color.BLACK;

    public CanvasPanel() {
        setLayout(new BorderLayout());

        canvas = new JPanel();
        canvas.setBackground(Color.WHITE);

        colorPalette = new ColorPalette();
        colorPalette.setPreferredSize(new Dimension(30,500));

        eraseButton = new JButton("지우기");
        eraseButton.setSize(100,50);
        eraseButton.addActionListener(eraseButtonClicked);

        keyword = new JTextField("제시어: ");
        keyword.setSize(400,50);
        keyword.setBackground(Color.lightGray);
        keyword.setEditable(false);
        keyword.setHorizontalAlignment(JTextField.CENTER);

        // 타이머 레이블 추가 (제시어 옆)
        timerLabel = new JLabel("⏱ --", JLabel.CENTER);
        timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        timerLabel.setBackground(Color.lightGray);
        timerLabel.setOpaque(true);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 제시어와 타이머를 함께 표시하는 패널
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.lightGray);
        headerPanel.add(keyword, BorderLayout.CENTER);
        headerPanel.add(timerLabel, BorderLayout.EAST);

        add(colorPalette, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        add(eraseButton,BorderLayout.SOUTH);
    }

    public void paintCanvas (Point from, Point to ) {
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(currentColor);
        g2.drawLine(from.x, from.y, to.x, to.y);
    }
    public void eraseCanvas() {
        this.canvas.repaint();
    }

    //그림 내용 서버에 전송하기 (지우기 & 그리기)
    public void sendCanvas (Point from, Point to) {
        gameController.sendDrawing(from,to);
    }
    public void sendErase() {gameController.sendErase();}

    public void updateTimer(int time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                timerLabel.setText("⏱ " + time + "초");
                if (time <= 5) {
                    timerLabel.setForeground(Color.RED);
                } else if (time <= 10) {
                    timerLabel.setForeground(Color.ORANGE);
                } else {
                    timerLabel.setForeground(Color.BLACK);
                }
            }
        });
    }

    //수신한 메시지에 따라 화면 업데이트
    public void updateColor(String msg) {this.currentColor = new Color(Integer.parseInt(msg));}
    public void setKeyword(String word) {
        System.out.println("[DEBUG] 화면 갱신 요청 받음: " + word);
        keyword.setText("제시어: " +word);
    }
    public void emptyKeyWord(String word) {
        keyword.setText("제시어: ???");
    }

    public void disableCanvasDrawing() {
        canvas.removeMouseListener(drawingListener);
        canvas.removeMouseMotionListener(drawingListener);
        System.out.println("[DEBUG] 그리기 모드 비활성화됨");
    }

    public void enalbeCanvasDrawing() {
        canvas.removeMouseListener(drawingListener);
        canvas.removeMouseMotionListener(drawingListener);
        canvas.addMouseListener(drawingListener);
        canvas.addMouseMotionListener(drawingListener);
        System.out.println("[DEBUG] 그리기 모드 활성화됨");
    }

    MouseAdapter drawingListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (!isEnabled()) return;
            lastPoint = e.getPoint();
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            lastPoint = null;
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            Point current = e.getPoint();

            if(lastPoint != null) {
                sendCanvas(lastPoint, current);
            }
            lastPoint = current;
        }
    };

    ActionListener eraseButtonClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendErase();
        }
    };

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        this.colorPalette.setGameController(gameController);
    }
}
