package GUI.SubPanels;

import ChessObjects.Board;
import ChessObjects.PieceTypes.Team;
import GUI.ChessPanel;
import GUI.ChessPanels.LocalChessPanel;
import GUI.MenuPanel;
import GUI.Utilities.RoundButton;
import GUI.Utilities.Slider;
import Support.AudioPlayer;
import Support.FileEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class LocalPanel extends JPanel {

    private final MenuPanel menuPanel;

    private RoundButton localButton;
    private RoundButton botButton;
    private Slider slider;

    public LocalPanel(MenuPanel menuPanel){
        this.menuPanel = menuPanel;

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

                Board board = new Board();

                FileEditor fileEditor = new FileEditor();
                board.setPositionByString(fileEditor.read(new File("save/startPosition/defaultPosition.json")));

                boolean turns = slider.getSelection() == 1;

                new LocalChessPanel(menuPanel.frame, new Dimension(1000, 800), board, Team.White, turns);
                menuPanel.frame.setResizable(true);
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
