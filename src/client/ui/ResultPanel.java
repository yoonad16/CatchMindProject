package client.ui;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JFrame {

    public ResultPanel(String msg) {
        String[] tokens = msg.split(":");
        setSize(800,500);
        setLayout(new BorderLayout());

        JTextArea result = new JTextArea();

        for (int i=1; i<tokens.length; i++) {
            result.append(tokens[i]);
            result.append("\n");
        }

        add(result,BorderLayout.CENTER);
        setVisible(true);
    }

}
