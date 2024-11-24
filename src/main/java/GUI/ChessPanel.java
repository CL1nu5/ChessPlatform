package GUI;

import ChessObjects.Board;
import DataObjects.DisplayBoard;

import javax.swing.*;
import java.awt.*;

public class ChessPanel extends JPanel {
    //Constants
    private final Color LIGHT_COLOR = new Color(246,246,246,255);
    private final Color DARK_COLOR = new Color(79,168,210,255);
    private final Color RIM_COLOR =  new Color(0,0,0,255);
    private final Color BACKGROUND_COLOR = new Color(246,246,246,255);


    //gui-Objects
    private Frame frame;

    private Board chessBoard;
    private Dimension displaySize;

    public ChessPanel(Frame frame, Board chessBoard, Dimension displaySize){
        this.chessBoard = chessBoard;
        this.displaySize = displaySize;

        this.setPreferredSize(displaySize);
        this.setOpaque(true);
        this.setBackground(BACKGROUND_COLOR);

        frame.switchPanel(this);
    }

    /* paint methods */
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        //paint all objects
        paintBoard(g2d);
    }

    public void paintBoard(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);

        for (int y = 0; y < sizes.fieldCount; y++){
            for (int x = 0; x < sizes.fieldCount; x++){
                //getting current position on board
                Point boardPosition = sizes.getIndexPosition(x, y);

                //rim
                if (sizes.isInRim(boardPosition)){
                    g.setColor(RIM_COLOR);
                    g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);
                }
                //nor rim
                else {
                    //setting field color
                    if ((x + y) % 2 == 0)
                        g.setColor(LIGHT_COLOR);
                    else
                        g.setColor(DARK_COLOR);

                    g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);
                }

            }
        }
    }

    /* position methods */


}
