package GUI.SubObjects;

import GUI.ChessPanel;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JPanel {

    protected double scale;
    protected static final int arc = 25;
    protected String buttonText;

    public RoundButton(double scale, String buttonText){
        this.setOpaque(false);
        this.scale = scale;
        this.buttonText = buttonText;
    }

    public Rectangle getRealBounds(){
        Rectangle bounds = getBounds();

        Dimension scaleSize = new Dimension((int)(bounds.width * scale), bounds.height);
        Point scalePos = new Point((bounds.width - scaleSize.width) / 2, 0);

        return new Rectangle(scalePos, scaleSize);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Rectangle bounds = getRealBounds();

        //paint background
        g2d.setColor(ChessPanel.MOVE_COLOR);
        g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arc, arc);

        //paint text
        Font font = new Font("Serif", Font.PLAIN, 20);
        paintText(g2d, bounds, buttonText, font, ChessPanel.LIGHT_COLOR);
    }

    public void paintText(Graphics2D g, Rectangle bounds, String text, Font font, Color color){
        g.setFont(font);
        g.setColor(color);

        Dimension textSize = StringEditor.getStringSize(text, font);

        int x = bounds.x + (bounds.width - textSize.width) / 2;
        int y = bounds.y + bounds.height - (bounds.height - textSize.height) / 2;

        g.drawString(text, x, y);
    }
}
