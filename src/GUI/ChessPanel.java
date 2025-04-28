package GUI;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
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
    protected Frame frame;
    protected Dimension displaySize;
    protected Point mousePos;

    //GameObjects
    public Board chessBoard;
    protected Team direction;
    protected Piece selectedPiece, grabbedPiece;

    //states
    public boolean gameOver;
    protected Team winner;

    public ChessPanel(Frame frame, Dimension displaySize, Board chessBoard, Team direction) {
        this.frame = frame;
        this.chessBoard = chessBoard;
        this.direction = direction;
        this.displaySize = displaySize;
        this.gameOver = false;
        this.winner = null;

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

    //returns: 0 no winner, 2 remis, -1 black wins, 1 white wins
    public void checkGameOver(){
        if (!chessBoard.getMoves().isEmpty()){
            return;
        }

        gameOver = true;

        //checks if enemy is checking
        chessBoard.switchTeam();
        for (Move move: chessBoard.getMoves()){
            if (move.isKingCaptured()){
                chessBoard.switchTeam();
                winner = chessBoard.activePlayer.getOpposite();
            }
        }
    }

    /* mouse listener methods - needed */
    @Override
    public void mousePressed(MouseEvent e) {
        //won't react, if the game is over
        if (gameOver){
            return;
        }

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
        //won't react, if the game is over
        if (gameOver){
            new MenuPanel(frame);
            return;
        }

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
        //won't react, if the game is over
        if (gameOver){
            return;
        }

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

        if (gameOver) {
            paintGameOver(g2d);
        }
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
        Dimension stringSize = StringEditor.getStringSize("8", font);

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

    protected abstract void paintGameOver(Graphics2D g);


    protected void paintGameOver(Graphics2D g, String text) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        Point start = sizes.getRealPositionOfSquare(new Point(0, 4));
        Point end = sizes.getRealPositionOfSquare(new Point(10, 6));

        Dimension size = new Dimension(displaySize.width, end.y - start.y);

        g.setColor(ChessPanel.MOVE_COLOR);
        g.fillRect(0, start.y, size.width, size.height);

        g.setColor(ChessPanel.LIGHT_COLOR);
        g.setFont(new Font("Serif", Font.PLAIN, 50));

        Dimension stringSize = StringEditor.getStringSize(text, g.getFont());

        g.drawString(text, (size.width - stringSize.width) / 2, start.y + size.height - (size.height - stringSize.height) / 2);
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