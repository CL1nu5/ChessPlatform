package GUI.Utilities;

import GUI.ChessPanel;
import GUI.SubPanels.OptionPanel;
import Support.AudioPlayer;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SwitchButton extends RoundButton {

    OptionPanel panel;
    public int selection;

    public SwitchButton(OptionPanel panel, int selection, double scale){
        super(scale, "");

        this.panel = panel;
        this.selection = selection;

        this.setPreferredSize(new Dimension(30, 30));
    }

    public Rectangle getRealBounds(int select){
        Rectangle bounds = super.getRealBounds();
        int halfWidth = bounds.width / 2;

        if (select == 0){
            return new Rectangle(bounds.x, bounds.y, halfWidth, bounds.height);
        }

        return new Rectangle(bounds.x + halfWidth, bounds.y, halfWidth, bounds.height);
    }

    /* action handling */
    public void clickAction(MouseEvent e){
        //checks what side was clicked
        for(int i = 0; i <= 1; i++){
            //needs to be the clicked side
            if (!getRealBounds(i).contains(e.getPoint())){
                continue;
            }

            //can't be the same side as the selected one
            if (selection == i){
                AudioPlayer.playSound("res/sounds/click2.wav");
                continue;
            }

            AudioPlayer.playSound("res/sounds/click1.wav");
            selection = i;
            panel.setSelectedPanel();
        }
    }

    /* paint methods */
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        paintSelection(g2d);
        paintText(g2d);
    }

    public void paintSelection(Graphics2D g){
        g.setColor(ChessPanel.CAPTURE_MOVE_COLOR);
        Rectangle bound = getRealBounds(selection);
        int halfArc = arc / 2;

        //paints the selection rectangle
        g.fillRect(bound.x + (1 - selection) * halfArc, bound.y, bound.width - halfArc, bound.height);
        //paints the round edge at the beginning or end of the selection
        g.fillOval(bound.x + selection * (bound.width - arc), bound.y, arc, bound.height);
    }

    public void paintText(Graphics2D g){
        String[] text = {"play local - bot", "play with friends"};

        for (int i = 0; i < text.length; i++){
            paintText(g,getRealBounds(i), text[i], font, ChessPanel.LIGHT_COLOR);
        }
    }
}
