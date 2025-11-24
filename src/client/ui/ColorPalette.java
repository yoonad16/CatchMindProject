package client.ui;

import client.controller.GameController;
import client.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//빨강, 파랑, 초록, 노랑, 검정, 하양
public class ColorPalette extends JPanel {

    private Color[] colors = {Color.black, Color.darkGray, Color.lightGray, Color.red, Color.orange, Color.yellow, Color.green, new Color(0,70,42),
            Color.cyan, Color.blue, Color.white};
    private GameController gameController;

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
        gameController.sendColor(colorCode);
    }
    public void setGameController(GameController gameController) {this.gameController = gameController;}
}
