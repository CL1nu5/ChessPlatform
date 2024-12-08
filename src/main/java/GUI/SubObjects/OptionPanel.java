package GUI.SubObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel implements ActionListener {

    SwitchButton button;

    public OptionPanel(){
        this.setLayout(new BorderLayout(0, 5));

        //button
        button = new SwitchButton(0, 0.75);
        button.addActionListener(this);
        this.add(button, BorderLayout.NORTH);

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
