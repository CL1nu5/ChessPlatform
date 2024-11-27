package GUI;

import ChessObjects.*;
import ChessObjects.PieceTypes.Team;
import DataObjects.DisplayBoard;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;

public class ChessPanel extends JPanel implements MouseListener, MouseMotionListener {
    //Constants
    private final Color LIGHT_COLOR = new Color(216, 243, 220);
    private final Color DARK_COLOR = new Color(149, 213, 178);
    private final Color RIM_COLOR = new Color(8, 28, 21);
    private final Color BACKGROUND_COLOR = new Color(246, 246, 246);
    private final Color TEXT_COLOR = new Color(246, 246, 246);
    private final Color SELECTED_PIECE_COLOR = new Color(45, 106, 79);
    private final Color MOVE_COLOR = new Color(64, 145, 108);
    private final Color CAPTURE_MOVE_COLOR = new Color(27, 67, 50);


    //gui-Objects
    private Frame frame;
    Point mousePos;

    private Board chessBoard;
    private Team direction;
    private Dimension displaySize;
    private Piece selectedPiece, grabbedPiece;

    public ChessPanel(Frame frame, Board chessBoard, Dimension displaySize, Team direction) {
        this.chessBoard = chessBoard;
        this.displaySize = displaySize;
        this.direction = direction;
        this.selectedPiece = null;
        this.grabbedPiece = null;

        this.setPreferredSize(displaySize);
        this.setOpaque(true);
        this.setBackground(BACKGROUND_COLOR);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        frame.switchPanel(this);
    }

    /* paint methods */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        //paint all objects
        paintBoard(g2d);
        printSelection(g2d);
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
            String character = String.valueOf(Objects.requireNonNull(
                    Chess.translatePosToCoords(sizes.getDirectionPos(x, y, direction))).charAt(val));
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
                    double scale = 0.75; // scales the pieces to 75% of the field
                    Piece piece = chessBoard.getPiece(sizes.getDirectionPos(x, y, direction));

                    if (piece == grabbedPiece){
                        continue;
                    }

                    paintPiece(g, sizes, boardPosition, scale, piece, true);
                }
            }
        }

        if (grabbedPiece != null && mousePos != null){
            paintPiece(g, sizes, mousePos , 0.75, grabbedPiece, false);
        }
    }

    public void paintPiece(Graphics2D g, DisplayBoard sizes, Point boardPosition, double scale, Piece piece, boolean gridLocked) {
        if (piece == null){
            return;
        }

        Dimension imageSize = sizes.getScaledFiledSize(scale);

        if (gridLocked)
            boardPosition = sizes.getScaledPosition(boardPosition, scale);
        else
            boardPosition = new Point(boardPosition.x - imageSize.width / 2, boardPosition.y - imageSize.height / 2);

        Image pieceImage = new ImageIcon(
                "res/ChessPieces/" + piece.team + "/" + piece.getClass().getSimpleName() + ".png").getImage();
        g.drawImage(pieceImage, boardPosition.x, boardPosition.y, imageSize.width, imageSize.height, null);
    }

    public void printSelection(Graphics2D g){
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);
        if (selectedPiece == null){
            return;
        }

        Point pos = selectedPiece.currentPosition;
        paintField(g, pos.x, pos.y, sizes, SELECTED_PIECE_COLOR);

        paintMoves(g, sizes);
    }

    public void paintMoves(Graphics2D g, DisplayBoard sizes){
        for (Move move : selectedPiece.getMoves()){
            //set color
            Color color = MOVE_COLOR;
            if (move.isCaptureMove()){
                color = CAPTURE_MOVE_COLOR;
            }

            Point pos = move.postponedPosition;
            paintField(g, pos.x, pos.y, sizes, color);
        }
    }

    public void paintField(Graphics2D g, int x, int y, DisplayBoard sizes, Color primaryColor){
        Point pos = sizes.getActualPos(x, y, direction);
        pos = sizes.getIndexPosition(pos.x, pos.y);

        g.setColor(primaryColor);
        g.fillRect(pos.x, pos.y, sizes.fieldLength, sizes.fieldLength);
    }

    public Point getPressedFiled(Point pos, DisplayBoard sizes){
        if (!sizes.isInBoard(pos)){
            return null;
        }

        int x = (pos.x - sizes.startX) / sizes.fieldLength;
        int y = (pos.y - sizes.startY) / sizes.fieldLength;
        return sizes.getDirectionPos(x, y,direction);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);
        Piece piece = chessBoard.getPiece(getPressedFiled(e.getPoint(), sizes));
        mousePos = e.getPoint();

        if (piece != null && piece.team.isInSameTeam(chessBoard.activePlayer)){
            selectedPiece = piece;
            grabbedPiece = piece;
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        DisplayBoard sizes = new DisplayBoard(displaySize, chessBoard);
        Point releasePos = getPressedFiled(e.getPoint(), sizes);

        if (selectedPiece != null && releasePos != null){
            if (!selectedPiece.currentPosition.equals(releasePos)){
                //check if it is a viable move
                for (Move move : selectedPiece.getMoves()){
                    if (releasePos.equals(move.postponedPosition)){
                        chessBoard.executeMove(move);
                    }
                }

                selectedPiece = null;
            }
        }

        grabbedPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //paint grabbedPiece
        mousePos = e.getPoint();
        repaint();
    }

    /* not needed */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
