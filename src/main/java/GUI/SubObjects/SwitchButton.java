package GUI.SubObjects;

import GUI.ChessPanel;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;

public class SwitchButton extends JButton {

    private static final int arc = 25;
    public int selection;
    private final double scale;

    public SwitchButton(int selection, double scale){
        this.selection = selection;
        this.scale = scale;

        this.setPreferredSize(new Dimension(30, 30));
    }

    public void setSelection(int newSection){
        if (newSection != 0 && newSection != 1){
            return;
        }

        if (selection == newSection){
            return;
        }

        selection = newSection;
        repaint();
    }

    //scale the width by the scale and center the position
    public Rectangle getRealBounds(){
        Rectangle bounds = getBounds();

        Dimension scaledSize  = new Dimension((int)(bounds.width * scale), bounds.height);
        Point pos = new Point((bounds.width - scaledSize.width) / 2, bounds.y);

        return new Rectangle(pos.x, pos.y, scaledSize.width, scaledSize.height);
    }

    public Rectangle getRealBounds(int side){
        Rectangle bounds = getRealBounds();
        int newWidth = bounds.width / 2;

        if (side == 0){
            return new Rectangle(bounds.x, bounds.y, newWidth, bounds.height);
        }
        return new Rectangle(bounds.x + newWidth, bounds.y, newWidth, bounds.height);
    }

    public void paint(Graphics g){
        Rectangle bounds = getRealBounds();

        //paint background
        g.setColor(ChessPanel.MOVE_COLOR);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arc, arc);

        //paint rest
        paintSelection(g);
        paintText(g);
    }

    public void paintSelection(Graphics g){
        g.setColor(ChessPanel.CAPTURE_MOVE_COLOR);
        Rectangle bounds = getRealBounds(selection);
        int halfArc = arc / 2;

        g.fillRect(bounds.x + halfArc * (1 - selection), bounds.y, bounds.width - halfArc, bounds.height);
        g.fillRoundRect(bounds.x + (bounds.width - arc) * selection, bounds.y, arc, bounds.height, arc, arc);
    }

    public void paintText(Graphics g){
        g.setColor(ChessPanel.LIGHT_COLOR);
        g.setFont(new Font("Serif", Font.PLAIN, 20));

        String[] text = {"Local - Bot", "Server"};
        for (int i = 0 ; i < text.length; i++){
            Dimension textSize = StringEditor.getStringSize(text[i], g.getFont());
            Rectangle bounds = getRealBounds(i);

            int relativeX = (bounds.width - textSize.width) / 2;
            int relativeY = bounds.height - (bounds.height - textSize.height) / 2;

            g.drawString(text[i], bounds.x + relativeX, bounds.y + relativeY);
        }
    }
}
