package GUI;

import ChessObjects.Board;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import DataObjects.DisplayBoard;
import DataObjects.PointComparator;

import javax.swing.*;
import java.awt.*;

public class ChessPanel extends JPanel {
    //Constants
    private final Color LIGHT_COLOR = new Color(216, 243, 220);
    private final Color DARK_COLOR = new Color(149, 213, 178);
    private final Color RIM_COLOR = new Color(8, 28, 21);
    private final Color BACKGROUND_COLOR = new Color(246, 246, 246);
    private final Color TEXT_COLOR = new Color(246, 246, 246);
    private final Color SELECTED_PIECE_COLOR = new Color(45, 106, 79);
    private final Color MOVE_COLOR = new Color(64, 145, 108);
    private final Color CAPTURE_MOVE_COLOR = new Color(27, 67, 50);

    //JObjects
    Frame frame;
    Dimension displaySize;
    Point mousePos;

    //GameObjects
    Board chessBoard;
    Team direction;
    Piece selectedPiece, grabbedPiece;

    public ChessPanel(Frame frame, Dimension displaySize, Board chessBoard, Team direction){
        this.frame = frame;
        this.chessBoard = chessBoard;
        this.direction = direction;
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
        paintBoard(g2d);
        paintSelection(g2d);
        paintPieces(g2d);
    }

    //painting gameObjects in correct drawing order
    private void paintBoard(Graphics2D g){
        paintField(g);
        paintRim(g);
    }

    private void paintField(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        for (PointComparator pc: sizes.getFieldPositions(direction)){
            //select color
            if ((pc.from.x + pc.from.y) % 2 == 0){
                g.setColor(LIGHT_COLOR);
            }
            else {
                g.setColor(DARK_COLOR);
            }

            //paint field
            g.fillRect(pc.to.x, pc.to.y, sizes.fieldLength, sizes.fieldLength);
        }
    }

    private void paintRim(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        for (PointComparator pc: sizes.getRimPositions()){
            //paint rim square
            g.setColor(RIM_COLOR);
            g.fillRect(pc.to.x, pc.to.y, sizes.fieldLength, sizes.fieldLength);

            //paint text
            Font font = new Font("Serif", Font.PLAIN, 40);
            g.setFont(font);

            String character = sizes.getRimChar(pc.from, direction);
            Point stringPosition = sizes.getStringStartingPosition(pc.to, character, g.getFont());

            g.setColor(TEXT_COLOR);
            g.drawString(character, stringPosition.x, stringPosition.y);
        }
    }

    private void paintSelection(Graphics2D g){

    }

    private void paintPieces(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        for (int y = 0; y < chessBoard.pieces.length; y++){
            for (int x = 0; x < chessBoard.pieces[y].length; x++){
                //getting hte piece
                Piece piece = chessBoard.getPiece(new Point(x, y));

                //checking if piece can be painted
                if (piece == null){
                    continue;
                }

                if (piece == grabbedPiece){
                    continue;
                }

                double scale = 0.75; // 75% of grid size
                paintPiece(g, sizes, piece, scale, true);
            }
        }
    }

    private void paintPiece(Graphics2D g, DisplayBoard sizes, Piece piece, double scale, boolean gridlocked){
        Point position = sizes.getRealPositionOfDirectionalFieldSquare(piece.currentPosition, direction);
        Rectangle bounds = sizes.getScaledBounds(position, scale);

        if (!gridlocked){
            bounds.setLocation(bounds.x - bounds.width / 2, bounds.y - bounds.height / 2);
        }

        Image pieceImage = new ImageIcon(
                "res/ChessPieces/" + piece.team + "/" + piece.getClass().getSimpleName() + ".png").getImage();
        g.drawImage(pieceImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }
}
