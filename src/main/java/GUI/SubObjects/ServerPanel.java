package GUI.SubObjects;

import GUI.ChessPanel;
import Support.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ServerPanel extends JPanel {

    private RoundButton serverButton, joinButton;
    JTextField ipSelection;

    public ServerPanel(){
        this.setBackground(ChessPanel.LIGHT_COLOR);
        this.setLayout(new GridLayout(10, 1));

        //add separation
        addLabel("");

        //adding components
        addLabel("Start Server:");
        addLabel("start a server, your friend can join on");
        addServerButton();

        //add separation
        addLabel("");

        //adding components
        addLabel("Join Server:");
        addLabel("join the server of your friend (enter his ip below)");

        ipSelection = new JTextField("Enter ip");
        ipSelection.setHorizontalAlignment(SwingConstants.CENTER);

        ipSelection.setFont(new Font("Serif", Font.ITALIC, 18));
        ipSelection.setMaximumSize(new Dimension(200, 20));
        ipSelection.setBorder(null);

        //color ip selection
        ipSelection.setBackground(ChessPanel.LIGHT_COLOR);
        ipSelection.setForeground(ChessPanel.MOVE_COLOR);
        ipSelection.setSelectionColor(ChessPanel.MOVE_COLOR);
        ipSelection.setSelectedTextColor(ChessPanel.LIGHT_COLOR);

        this.add(ipSelection);

        addJoinButton();
    }

    public void addLabel(String text) {
        JLabel label = new JLabel(text, SwingUtilities.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));

        this.add(label);
    }

    public void addServerButton(){
        serverButton = new RoundButton(0.4, "start"){
            @Override
            public void clickAction(MouseEvent e){
                AudioPlayer.playSound("res/sounds/click1.wav");
                System.out.println("server start clicked");
            }
        };
        this.add(serverButton);
    }

    public void addJoinButton(){
        serverButton = new RoundButton(0.4, "start"){
            @Override
            public void clickAction(MouseEvent e){
                AudioPlayer.playSound("res/sounds/click1.wav");
                System.out.println("join clicked");
            }
        };
        this.add(serverButton);
    }
}
