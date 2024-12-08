package GUI.SubObjects;

import GUI.ChessPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel implements ActionListener {

    JPanel currentPanel;

    SwitchButton button;
    LocalPanel localPanel;
    ServerPanel serverPanel;

    public OptionPanel(){
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(ChessPanel.LIGHT_COLOR);

        //button
        addButton();

        //crate panels
        localPanel = new LocalPanel();
        serverPanel = new ServerPanel();

        //set selection
        setSelectedPanel();
    }

    public void addButton(){
        button = new SwitchButton(this, 0, 0.75);
        button.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            //get clicked side
            int side = -1;

            for (int i = 0 ; i <= 1; i++){
                //get relative position of the click
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                Point buttonPos = button.getLocationOnScreen();
                Point relativePos = new Point(mousePos.x - buttonPos.x, mousePos.y - buttonPos.y);

                if (button.getRealBounds(i).contains(relativePos)){
                    side = i;
                    break;
                }
            }

            if (side == -1){
                return;
            }

            button.setSelection(side);
        }
    }
}
