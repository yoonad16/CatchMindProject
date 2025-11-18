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

    public CanvasPanel() {
        setLayout(new BorderLayout());
        setSize(500,500);

        canvas = new JPanel();
        canvas.setBackground(Color.WHITE);
        keyword = new JTextField();
        keyword.setBackground(Color.lightGray);

        canvas.addMouseListener(drawingListener);
        canvas.addMouseMotionListener(drawingListener);

        add(canvas, BorderLayout.CENTER);
        add(keyword, BorderLayout.NORTH);
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

    MouseAdapter drawingListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
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
//                paintCanvas(lastPoint, current);
                sendCanvas(lastPoint, current);
            }
            lastPoint = current;
        }
    };

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
