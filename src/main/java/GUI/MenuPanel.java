package GUI;

import GUI.SubPanels.SelectionPanel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public Frame frame;
    private final Dimension displaySize = new Dimension(1000, 800);
    private final Dimension selectionSize = new Dimension(600, 400);

    //JObjects
    SelectionPanel selectionPanel;

    private final int fieldLength = 50;

    public MenuPanel(Frame frame) {
        this.frame = frame;
        this.frame.setResizable(false);

        this.setPreferredSize(displaySize);
        this.setLayout(null);

        Point selectionPos = new Point(
                (displaySize.width - selectionSize.width) / 2, (displaySize.height - selectionSize.height) / 2);
        selectionPanel = new SelectionPanel(selectionSize, selectionPos, this);
        this.add(selectionPanel);

        frame.switchPanel(this);
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintFields(g2d);
        paintDisplayField(g2d);

        selectionPanel.repaint();
    }

    public void paintFields(Graphics2D g) {
        for (int y = 0; y < displaySize.height / fieldLength; y++) {
            for (int x = 0; x < displaySize.width / fieldLength; x++) {
                //select color
                if ((x + y) % 2 == 0) {
                    g.setColor(ChessPanel.LIGHT_COLOR);
                } else {
                    g.setColor(ChessPanel.DARK_COLOR);
                }

                g.fillRect(x * fieldLength, y * fieldLength, fieldLength, fieldLength);
            }
        }
    }

    public void paintDisplayField(Graphics2D g){
        int arcSize = fieldLength * 2;

        Dimension size = new Dimension(selectionSize.width + arcSize, selectionSize.height + arcSize);
        Point position = new Point((displaySize.width - size.width) / 2, (displaySize.height - size.height) / 2);

        g.setColor(ChessPanel.LIGHT_COLOR);
        g.fillRoundRect(position.x, position.y, size.width, size.height, arcSize, arcSize);
    }
}
