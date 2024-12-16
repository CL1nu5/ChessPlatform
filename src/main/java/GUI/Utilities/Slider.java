package GUI.Utilities;

import GUI.ChessPanel;
import Support.AudioPlayer;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Slider extends JPanel implements MouseListener {

    private final String leftText, rightText;
    private int selection;

    private Dimension size;
    private final int padding = 2;

    public Slider(int selection, String leftText, String rightText){
        this.selection = selection;
        this.leftText = leftText;
        this.rightText = rightText;

        this.addMouseListener(this);
    }

    public int getSelection(){
        return selection;
    }

    private Rectangle getRealBounds(){
        Rectangle bounds = getBounds();

        if (size == null){
            size = new Dimension(45, bounds.height - padding * 2);
        }

        return new Rectangle((bounds.width - size.width) / 2, padding, size.width, size.height);
    }

    public void paint(Graphics g){
        Rectangle bounds = getBounds();

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //draw background
        g2d.setColor(ChessPanel.LIGHT_COLOR);
        g2d.fillRect(0, 0, bounds.width, bounds.height);

        drawSlider(g2d);
        drawText(g2d);
    }

    private void drawSlider(Graphics2D g){
        Rectangle bounds = getRealBounds();
        int arc = size.height;

        g.setColor(ChessPanel.MOVE_COLOR);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, (int) (arc / 1.5), arc);


        arc -= padding * 2;
        g.setColor(ChessPanel.LIGHT_COLOR);
        g.fillOval(bounds.x + padding * (1 - selection) + (bounds.width - arc - padding) * selection, padding * 2, arc, arc);
    }

    private void drawText(Graphics2D g){
        Rectangle bounds = getRealBounds();
        g.setColor(ChessPanel.MOVE_COLOR);
        g.setFont(new Font("Serif", Font.PLAIN, 18));

        //draw left text
        Dimension textSize = StringEditor.getStringSize(leftText, g.getFont());
        g.drawString(leftText, bounds.x - textSize.width - padding * 3, bounds.y + bounds.height - (bounds.height - textSize.height) / 2);

        //draw right
        textSize = StringEditor.getStringSize(rightText, g.getFont());
        g.drawString(rightText, bounds.x + bounds.width + padding * 3, bounds.y + bounds.height - (bounds.height - textSize.height) / 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Rectangle bounds = getRealBounds();

        if (bounds.contains(e.getPoint())){
            selection = 1 - selection;
            AudioPlayer.playSound("res/sounds/click1.wav");

            repaint();
        }
    }

    /* not needed */
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
