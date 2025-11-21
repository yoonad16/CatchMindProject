package client.ui;

import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasPanel extends JPanel {

    private JPanel canvas;
    private JTextField keyword;
    private Point lastPoint = null;
    private ViewController viewController;
    private JButton eraseButton;

    public CanvasPanel() {
        setLayout(new BorderLayout());
        setSize(500,500);

        canvas = new JPanel();
        canvas.setBackground(Color.WHITE);

        eraseButton = new JButton("지우기");
        eraseButton.setSize(100,50);
        eraseButton.addActionListener(eraseButtonClicked);

        keyword = new JTextField("제시어: ");
        keyword.setSize(400,50);
        keyword.setBackground(Color.lightGray);
        keyword.setEditable(false);
        keyword.setHorizontalAlignment(JTextField.CENTER);

        add(canvas, BorderLayout.CENTER);
        add(keyword, BorderLayout.NORTH);
        add(eraseButton,BorderLayout.SOUTH);
    }

    public void paintCanvas (Point from, Point to ) {
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        g2.drawLine(from.x, from.y, to.x, to.y);
    }

    public void sendCanvas (Point from, Point to) {
        viewController.sendDrawing(from,to);
    }

    public void eraseCanvas() {
        this.canvas.repaint();
    }

    public void sendErase() {
        viewController.sendErase("ERASE:");
    }

    public void setKeyword(String word) {
        System.out.println("[DEBUG] 화면 갱신 요청 받음: " + word);
        keyword.setText("제시어: " +word);
    }

    public void emptyKeyWord(String word) {
        keyword.setText("제시어: ???");
    }
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void disableCanvasDrawing() {
        canvas.removeMouseListener(drawingListener);
        canvas.removeMouseMotionListener(drawingListener);
    }

    public void enalbeCanvasDrawing() {
        canvas.addMouseListener(drawingListener);
        canvas.addMouseMotionListener(drawingListener);
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
}
