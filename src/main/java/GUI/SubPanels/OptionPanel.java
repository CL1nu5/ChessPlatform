package GUI.SubPanels;

import GUI.ChessPanel;
import GUI.MenuPanel;
import GUI.Utilities.SwitchButton;

import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel {

    JPanel currentPanel;
    MenuPanel menuPanel;

    SwitchButton button;
    LocalPanel localPanel;
    ServerPanel serverPanel;

    public OptionPanel(MenuPanel menuPanel){
        this.menuPanel = menuPanel;
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(ChessPanel.LIGHT_COLOR);

        //button
        addButton();

        //crate panels
        localPanel = new LocalPanel(menuPanel);
        serverPanel = new ServerPanel(menuPanel);

        //set selection
        setSelectedPanel();
    }

    public void addButton(){
        button = new SwitchButton(this, 0, 0.75);
        this.add(button, BorderLayout.NORTH);
    }

    public void setSelectedPanel(){
        int selection = button.selection;

        //remove old panel
        if (currentPanel != null){
            this.remove(currentPanel);
        }

        //set selection
        if (selection == 0){
            currentPanel = localPanel;
        }
        else {
            currentPanel = serverPanel;
        }

        this.add(currentPanel, BorderLayout.CENTER);
        this.revalidate();
        repaint();
    }
}
