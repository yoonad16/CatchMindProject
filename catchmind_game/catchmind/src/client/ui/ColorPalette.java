package client.ui;

import client.controller.ViewController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//빨강, 파랑, 초록, 노랑, 검정, 하양
public class ColorPalette extends JPanel {

    private Color[] colors = {Color.black, Color.blue, Color.red, Color.green, Color.white, Color.yellow};
    private ViewController viewController;

    public ColorPalette() {
        setPreferredSize(new Dimension(30,150));
        setLayout (new FlowLayout(FlowLayout.CENTER));

        for (Color c : colors) {
            JButton colorButton = new JButton();
            colorButton.setPreferredSize(new Dimension(20,20));
            colorButton.setBackground(c);
            colorButton.setOpaque(true);
            colorButton.setBorderPainted(false);

            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendCurrentColor(c);
                }
            });

            add(colorButton);
        }
    }

    public void sendCurrentColor(Color c) {
        String colorCode = String.valueOf(c.getRGB());
        viewController.sendColor(colorCode);
    }
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
