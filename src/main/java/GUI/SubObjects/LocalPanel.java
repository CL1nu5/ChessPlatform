package GUI.SubObjects;

import GUI.ChessPanel;

import javax.swing.*;
import java.awt.*;

public class LocalPanel extends JPanel {

    JPanel localButton;
    JPanel botButton;

    public LocalPanel(){
        this.setBackground(ChessPanel.DARK_COLOR);
        this.setLayout(new GridLayout(9, 1));

        //adding components

        addLabel("Local:");
        localButton = new RoundButton(0.4, "play local");
        this.add(localButton);

        addLabel("Bot:");
        botButton = new RoundButton(0.4, "play against bot");
        this.add(botButton);
    }

    public void addLabel(String text) {
        JLabel label = new JLabel(text, SwingUtilities.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));

        this.add(label);
    }
}
