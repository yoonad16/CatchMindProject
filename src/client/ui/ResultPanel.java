package client.ui;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JFrame {

    public ResultPanel(String msg) {
        String[] tokens = msg.split(":");
        setSize(800,500);
        setLayout(new BorderLayout());

        JTextArea result = new JTextArea("결과\n");
        result.setEditable(false);

        for (String t : tokens) {
            result.append(t+"\n");
        }

        add(result,BorderLayout.CENTER);
        setVisible(true);
    }

}
