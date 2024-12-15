package GUI.SubPanels;

import ChessObjects.Board;
import Client.Client;
import GUI.ChessPanel;
import GUI.MenuPanel;
import GUI.Utilities.HintTextField;
import GUI.Utilities.RoundButton;
import GUI.Utilities.ServerStarter;
import Support.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.ConnectException;

public class ServerPanel extends JPanel{

    MenuPanel menuPanel;

    private RoundButton serverButton, joinButton;
    private HintTextField ipSelection;
    private JLabel infoLabel;

    public ServerPanel(MenuPanel menuPanel){
        this.menuPanel = menuPanel;

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
        addLabel("join the server of your friend");

        addIpSelection();

        addJoinButton();

        infoLabel = new JLabel("", SwingUtilities.CENTER);
        infoLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        this.add(infoLabel);
    }

    /* stating methods */
    public void startServer(){
        new ServerStarter(4891).start();
    }

    public void joinServer(String ip){
        try {
            new Client(ip, 4891, menuPanel.frame, new Dimension(1000, 800));

        } catch(ConnectException e){
            infoLabel.setText("Connection failed!");
        }
    }

    /* adding component methods */
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
                startServer();
                joinServer("localhost");
            }
        };
        this.add(serverButton);
    }

    public void addJoinButton(){
        serverButton = new RoundButton(0.4, "join"){
            @Override
            public void clickAction(MouseEvent e){
                AudioPlayer.playSound("res/sounds/click1.wav");
                joinServer(ipSelection.getText());
            }
        };
        this.add(serverButton);
    }

    public void addIpSelection(){
        ipSelection = new HintTextField("Enter Ip here"){
            @Override
            public void enterPressed(){
                AudioPlayer.playSound("res/sounds/enter.wav");
                joinServer(ipSelection.getText());
            }
        };
        this.add(ipSelection);
    }
}
