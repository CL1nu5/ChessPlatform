package GUI;

import ChessObjects.Board;
import ChessObjects.Chess;
import DataObjects.DisplayBoard;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessPanel extends JPanel {
    //Constants
    private final Color LIGHT_COLOR = new Color(246,246,246,255);
    private final Color DARK_COLOR = new Color(79,168,210,255);
    private final Color RIM_COLOR =  new Color(0,0,0,255);
    private final Color BACKGROUND_COLOR = new Color(246,246,246,255);
    private final Color TEXT_COLOR = new Color(246,246,246,255);


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

    //prints the board and rim in the center of the chess panel
    public void paintBoard(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);

        //get perfect font for field size : string is a placeholder of 1 character
        Font font = StringEditor.getPerfectSizedFont(
                "Arial", " ", sizes.getFieldSize(),12, 50);
        g.setFont(font);

        for (int y = 0; y < sizes.fieldCount; y++){
            for (int x = 0; x < sizes.fieldCount; x++){
                //getting current position on board
                Point boardPosition = sizes.getIndexPosition(x, y);

                //rim
                if (sizes.isInRim(boardPosition)){
                    //print pox
                    g.setColor(RIM_COLOR);
                    g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);

                    //print text
                    //character selection
                    int val = -1;
                    if (sizes.isInXRim(boardPosition) && ! sizes.isInYRim(boardPosition))
                        val = 1;
                    else if (!sizes.isInXRim(boardPosition) && sizes.isInYRim(boardPosition)) {
                        val = 0;
                    }

                    if (val != -1){
                        String character = String.valueOf(Objects.requireNonNull(
                                Chess.translatePosToCoords(new Point(x - 1, y - 1))).charAt(val));
                        Point pos = sizes.getStringStartingPos(x, y, character, font);

                        g.setColor(TEXT_COLOR);
                        g.drawString(character, pos.x, pos.y);
                    }
                }

                //not rim
                else {
                    if ((x + y) % 2 == 0)
                        g.setColor(LIGHT_COLOR);
                    else
                        g.setColor(DARK_COLOR);

                    g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);
                }
            }
        }
    }
}
