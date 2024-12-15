package GUI.SubPanels;

import GUI.ChessPanel;
import GUI.MenuPanel;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel {

    //JObjects
    JLabel headLabel, bottomLabel;
    OptionPanel optionPanel;
    MenuPanel menuPanel;

    public SelectionPanel(Dimension displaySize, Point position, MenuPanel menuPanel){
        this.setSize(displaySize);
        this.setLocation(position);
        this.menuPanel = menuPanel;

        this.setLayout(new BorderLayout(0, 10));

        //add components
        addHeadLabel();
        addBottomLabel();

        optionPanel = new OptionPanel(menuPanel);
        this.add(optionPanel, BorderLayout.CENTER);

        //style settings
        this.setBackground(ChessPanel.LIGHT_COLOR);
    }

    //creates the head label and adds it to the panel
    public void addHeadLabel(){
        headLabel = new JLabel("Chess-Game", SwingUtilities.CENTER);
        headLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        //setting size
        Dimension size = StringEditor.getStringSize(headLabel.getText(), headLabel.getFont());
        headLabel.setPreferredSize(new Dimension(size.width + 30, size.height + 30));

        //color
        headLabel.setForeground(ChessPanel.RIM_COLOR);
        headLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, ChessPanel.RIM_COLOR));

        this.add(headLabel, BorderLayout.NORTH);
    }

    public void addBottomLabel(){
        bottomLabel = new JLabel("© Linus Pummer", SwingUtilities.CENTER);
        bottomLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        //color
        bottomLabel.setForeground(Color.lightGray);

        this.add(bottomLabel, BorderLayout.SOUTH);
    }


}
