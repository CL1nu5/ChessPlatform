package GUI;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import Client.Client;
import DataObjects.DisplayBoard;
import DataObjects.PointComparator;
import Support.StringEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ChessPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {
    //Constants
    public static final Color LIGHT_COLOR = new Color(216, 243, 220);
    public static final Color DARK_COLOR = new Color(149, 213, 178);
    public static final Color RIM_COLOR = new Color(8, 28, 21);
    public static final Color BACKGROUND_COLOR = new Color(246, 246, 246);
    public static final Color TEXT_COLOR = new Color(246, 246, 246);
    public static final Color SELECTED_PIECE_COLOR = new Color(45, 106, 79);
    public static final Color MOVE_COLOR = new Color(64, 145, 108);
    public static final Color CAPTURE_MOVE_COLOR = new Color(27, 67, 50);

    //JObjects
    protected Dimension displaySize;
    protected Point mousePos;

    //GameObjects
    public Board chessBoard;
    protected Team direction;
    protected Piece selectedPiece, grabbedPiece;

    public ChessPanel(Frame frame, Dimension displaySize, Board chessBoard, Team direction) {
        this.chessBoard = chessBoard;
        this.direction = direction;
        this.displaySize = displaySize;

        this.setPreferredSize(displaySize);
        this.setOpaque(true);
        this.setBackground(BACKGROUND_COLOR);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);

        frame.switchPanel(this);
    }

    /* action methods */

    protected abstract void pressed(Piece piece);

    protected abstract void execute(Move move);

    /* mouse listener methods - needed */
    @Override
    public void mousePressed(MouseEvent e) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim
        mousePos = e.getPoint();

        Point fieldPos = sizes.getDirectionalFieldSquare(mousePos, direction);
        if (fieldPos == null){
            return;
        }

        Piece piece = chessBoard.getPiece(fieldPos);
        if (piece != null){
            pressed(piece);
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim
        mousePos = e.getPoint();

        grabbedPiece = null;
        repaint();

        Point fieldPos = sizes.getDirectionalFieldSquare(mousePos, direction);
        if (selectedPiece == null || fieldPos == null){
            return;
        }
        if (selectedPiece.currentPosition.equals(fieldPos)){
            return;
        }

        for (Move move : selectedPiece.getMoves()){
            if (fieldPos.equals(move.postponedPosition)){
                execute(move);
            }
        }

        selectedPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    /* paint methods */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        paintBoard(g2d);
        paintSelection(g2d);
        paintPieces(g2d);
    }

    //painting gameObjects in correct drawing order
    private void paintBoard(Graphics2D g) {
        paintField(g);
        paintRim(g);
    }

    private void paintField(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        for (PointComparator pc : sizes.getFieldPositions(direction)) {
            //select color
            if ((pc.from.x + pc.from.y) % 2 == 0) {
                g.setColor(LIGHT_COLOR);
            } else {
                g.setColor(DARK_COLOR);
            }

            //paint field
            g.fillRect(pc.to.x, pc.to.y, sizes.fieldLength, sizes.fieldLength);
        }
    }

    private void paintRim(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        //character setup
        Font font = new Font("Serif", Font.PLAIN, 40);
        g.setFont(font);
        Dimension stringSize = StringEditor.getStringSize(" ", font);

        for (PointComparator pc : sizes.getRimPositions()) {
            //paint rim square
            g.setColor(RIM_COLOR);
            g.fillRect(pc.to.x, pc.to.y, sizes.fieldLength, sizes.fieldLength);

            //paint text
            String character = sizes.getRimChar(pc.from, direction);
            Point stringPosition = sizes.getStringStartingPosition(pc.to, stringSize);

            g.setColor(TEXT_COLOR);
            g.drawString(character, stringPosition.x, stringPosition.y);
        }
    }

    private void paintSelection(Graphics2D g) {
        if (selectedPiece == null) {
            return;
        }

        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim
        paintField(g, sizes, selectedPiece.currentPosition, SELECTED_PIECE_COLOR);

        for (Move move : selectedPiece.getMoves()) {
            if (move.isCaptureMove()) {
                paintField(g, sizes, move.postponedPosition, CAPTURE_MOVE_COLOR);
            } else {
                Point realPos = sizes.getRealPositionOfDirectionalFieldSquare(move.postponedPosition, direction);
                g.setColor(MOVE_COLOR);

                Rectangle bounds = sizes.getScaledBounds(realPos, 0.6);
                g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }

    public void paintField(Graphics2D g, DisplayBoard sizes, Point fieldPos, Color color) {
        Point realPos = sizes.getRealPositionOfDirectionalFieldSquare(fieldPos, direction);

        g.setColor(color);
        g.fillRect(realPos.x, realPos.y, sizes.fieldLength, sizes.fieldLength);
    }

    private void paintPieces(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        for (int y = 0; y < chessBoard.pieces.length; y++) {
            for (int x = 0; x < chessBoard.pieces[y].length; x++) {
                //getting hte piece
                Piece piece = chessBoard.getPiece(new Point(x, y));

                //checking if piece can be painted
                if (piece == null) {
                    continue;
                }

                if (piece == grabbedPiece) {
                    continue;
                }

                double scale = 0.75; // 75% of grid size
                paintPiece(g, sizes, piece, null, scale, true);
            }
        }

        if (grabbedPiece != null){
            double scale = 0.75; // 75% of grid size
            paintPiece(g, sizes, grabbedPiece, mousePos, scale, false);
        }
    }

    private void paintPiece(Graphics2D g, DisplayBoard sizes, Piece piece, Point piecePos, double scale, boolean gridlocked) {
        if (piecePos == null){
            piecePos = sizes.getRealPositionOfDirectionalFieldSquare(piece.currentPosition, direction);
        }

        Rectangle bounds = sizes.getScaledBounds(piecePos, scale);

        if (!gridlocked) {
            bounds.setLocation(piecePos.x - bounds.width / 2, piecePos.y - bounds.height / 2);
        }

        Image pieceImage = new ImageIcon(
                "res/ChessPieces/" + piece.team + "/" + piece.getClass().getSimpleName() + ".png").getImage();
        g.drawImage(pieceImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    /* adapting to frame changes */
    @Override
    public void componentResized(ComponentEvent e) {
        Dimension frameSize = e.getComponent().getSize();

        displaySize = new Dimension(frameSize.width - frameSize.width % 10, frameSize.height - frameSize.height % 10);
        repaint();
    }

    /* mouse listener methods - not needed */
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}
