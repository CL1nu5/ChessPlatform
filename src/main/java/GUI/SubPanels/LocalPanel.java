package GUI.SubPanels;

import GUI.ChessPanel;
import GUI.Utilities.RoundButton;
import GUI.Utilities.Slider;
import Support.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class LocalPanel extends JPanel {

    RoundButton localButton;
    RoundButton botButton;
    Slider slider;

    public LocalPanel(){
        this.setBackground(ChessPanel.LIGHT_COLOR);
        this.setLayout(new GridLayout(10, 1));

        //add separation
        addLabel("");

        //adding components
        addLabel("Local:");
        addLabel("playing a game of chess against your friends or by yourself on one device");
        slider = new Slider(0, "stays", "turns");
        this.add(slider);
        addLocalButton();

        //add separation
        addLabel("");

        //adding components
        addLabel("Bot:");
        addLabel("playing a game of chess against a self programed chess bot");
        addBotButton();
    }

    public void addLabel(String text) {
        JLabel label = new JLabel(text, SwingUtilities.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));

        this.add(label);
    }

    public void addLocalButton(){
        localButton = new RoundButton(0.4, "play local"){
            @Override
            public void clickAction(MouseEvent e){
                AudioPlayer.playSound("res/sounds/click1.wav");
                System.out.println("local clicked");
            }
        };
        this.add(localButton);
    }

    public void addBotButton(){
        botButton = new RoundButton(0.4, "play against bot"){
            @Override
            public void clickAction(MouseEvent e){
                AudioPlayer.playSound("res/sounds/click1.wav");
                System.out.println("bot clicked");
            }
        };
        this.add(botButton);
    }
}
