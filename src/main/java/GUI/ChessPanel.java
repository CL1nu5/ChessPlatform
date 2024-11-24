package GUI;

import ChessObjects.Board;
import ChessObjects.Chess;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import DataObjects.DisplayBoard;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessPanel extends JPanel {
    //Constants
    private final Color LIGHT_COLOR = new Color(246, 246, 246, 255);
    private final Color DARK_COLOR = new Color(79, 168, 210, 255);
    private final Color RIM_COLOR = new Color(0, 0, 0, 255);
    private final Color BACKGROUND_COLOR = new Color(246, 246, 246, 255);
    private final Color TEXT_COLOR = new Color(246, 246, 246, 255);


    //gui-Objects
    private Frame frame;

    private Board chessBoard;
    private Team direction;
    private Dimension displaySize;

    public ChessPanel(Frame frame, Board chessBoard, Dimension displaySize, Team direction) {
        this.chessBoard = chessBoard;
        this.displaySize = displaySize;
        this.direction = direction;

        this.setPreferredSize(displaySize);
        this.setOpaque(true);
        this.setBackground(BACKGROUND_COLOR);

        frame.switchPanel(this);
    }

    /* paint methods */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        //paint all objects
        paintBoard(g2d);
        paintPieces(g2d);
    }

    //prints the board and rim in the center of the chess panel
    public void paintBoard(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);

        //get perfect font for field size : string is a placeholder of 1 character
        Font font = StringEditor.getPerfectSizedFont(
                "Serif", " ", sizes.getFieldSize(), 12, 50);
        g.setFont(font);

        for (int y = 0; y < sizes.fieldCount; y++) {
            for (int x = 0; x < sizes.fieldCount; x++) {
                //getting current position on board
                Point boardPosition = sizes.getIndexPosition(x, y);

                //rim
                if (sizes.isInRim(boardPosition)) {
                    paintRim(g, x, y, boardPosition, sizes, font);
                    continue;
                }

                //not rim
                paintFields(g, x, y, boardPosition, sizes);
            }
        }
    }

    public void paintRim(Graphics2D g, int x, int y, Point boardPosition, DisplayBoard sizes, Font font) {
        //print pox
        g.setColor(RIM_COLOR);
        g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);

        //print text
        //character selection
        int val = -1;
        if (sizes.isInXRim(boardPosition) && !sizes.isInYRim(boardPosition))
            val = 1;
        else if (!sizes.isInXRim(boardPosition) && sizes.isInYRim(boardPosition)) {
            val = 0;
        }

        if (val != -1) {
            int dv = direction.value;
            String character = String.valueOf(Objects.requireNonNull(
                    Chess.translatePosToCoords(sizes.getDirectionPos(x - dv, y - dv, direction))).charAt(val));
            Point pos = sizes.getStringStartingPos(x, y, character, font);

            g.setColor(TEXT_COLOR);
            g.drawString(character, pos.x, pos.y);
        }
    }

    public void paintFields(Graphics2D g, int x, int y, Point boardPosition, DisplayBoard sizes) {
        //position doesn't need to be aligned in a direction, because it is the same for both
        if ((x + y) % 2 == 0)
            g.setColor(LIGHT_COLOR);
        else
            g.setColor(DARK_COLOR);

        g.fillRect(boardPosition.x, boardPosition.y, sizes.fieldLength, sizes.fieldLength);
    }

    public void paintPieces(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);

        for (int y = 0; y < sizes.fieldCount; y++) {
            for (int x = 0; x < sizes.fieldCount; x++) {
                Point boardPosition = sizes.getIndexPosition(x, y);

                if (sizes.isInBoard(boardPosition)) {
                    double scale = 0.75; // scales the pieces to 80% of the field
                    paintPiece(g, sizes, boardPosition, scale, x - direction.value, y - direction.value);
                }
            }
        }

    }

    public void paintPiece(Graphics2D g, DisplayBoard sizes, Point boardPosition, double scale, int x, int y) {
        Piece piece = chessBoard.getPiece(sizes.getDirectionPos(x, y, direction));
        boardPosition = sizes.getScaledPosition(boardPosition, scale);

        if (piece == null){
            return;
        }

        Dimension imageSize = sizes.getScaledFiledSize(scale);

        Image pieceImage = new ImageIcon(
                "res/ChessPieces/" + piece.team + "/" + piece.getClass().getSimpleName() + ".png").getImage();
        g.drawImage(pieceImage, boardPosition.x, boardPosition.y, imageSize.width, imageSize.height, null);
    }
}
