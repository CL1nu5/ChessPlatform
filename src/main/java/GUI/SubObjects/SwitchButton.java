package GUI.SubObjects;

import java.awt.*;

public class SwitchButton extends RoundButton {

    OptionPanel panel;
    public int selection;

    public SwitchButton(OptionPanel panel, int selection, double scale){
        super(scale, "");

        this.panel = panel;
        this.selection = selection;

        this.setPreferredSize(new Dimension(30, 30));
    }
}
