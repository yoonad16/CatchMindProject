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

        add(colorPalette, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        add(keyword, BorderLayout.NORTH);
        add(eraseButton,BorderLayout.SOUTH);
    }


    //브로드캐스트 수신 시: 캔버스 그림 그려지기 & 지우기
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

    //수신한 메시지에 따라 화면 업데이트
    public void updateColor(String msg) {this.currentColor = new Color(Integer.parseInt(msg));}
    public void setKeyword(String word) {keyword.setText("제시어: " +word);}
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
