package GUI.SubObjects;

import GUI.ChessPanel;

import javax.swing.*;
import java.awt.*;

public class LocalPanel extends JPanel {

    JPanel localButton;
    JPanel botButton;

    public LocalPanel(){
        this.setBackground(ChessPanel.LIGHT_COLOR);
        this.setLayout(new GridLayout(10, 1));

        //add separation
        addLabel("");

        //adding components
        addLabel("Local:");
        addLabel("playing a game of chess against your friends or by yourself on one device");
        localButton = new RoundButton(0.4, "play local");
        this.add(localButton);

        //add separation
        addLabel("");

        addLabel("Bot:");
        addLabel("playing a game of chess against a self programed chess bot");
        botButton = new RoundButton(0.4, "play against bot");
        this.add(botButton);
    }

    public void addLabel(String text) {
        JLabel label = new JLabel(text, SwingUtilities.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));

        this.add(label);
    }
}
