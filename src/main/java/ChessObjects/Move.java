package ChessObjects;

import ChessObjects.Pieces.King;

import java.awt.*;
import java.util.logging.Logger;

public class Move implements Cloneable{
    public Piece movingPiece, capturedPiece;
    public Point previousPosition, postponedPosition;
    public Move connectedMove;
    public Board board;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //general constructor
    public Move(Piece movingPiece, Piece capturedPiece, Point postponedPosition, Move connectedMove) {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.previousPosition = movingPiece.currentPosition;
        this.postponedPosition = postponedPosition;
        this.connectedMove = connectedMove;
        this.board = movingPiece.board;
    }

    //constructor for basic moves (non capture)
    public Move(Piece movingPiece, Point postponedPosition) {
        this(movingPiece, null, postponedPosition, null);
    }

    /* capture methods */
    //checks if a piece was captured by this move
    public boolean isCaptureMove() {
        return capturedPiece != null;
    }

    public boolean isKingCaptured() {
        if (!isCaptureMove()) {
            return false;
        }
        return capturedPiece instanceof King;
    }

    /* updating moves */
    public void execute() {
        removeCapturedPiece();

        if (!movingPiece.setPosition(postponedPosition)) {
            logger.warning("move:execute - position update not possible");
        }

        if (connectedMove != null) {
            connectedMove.execute();
        }
    }

    public void undo() {
        if (connectedMove != null) {
            connectedMove.undo();
        }

        if (!movingPiece.setPosition(previousPosition)) {
            logger.warning("move:undo - position update not possible");
        }

        addCapturedPiece();
    }

    //execution methods
    public void removeCapturedPiece() {
        if (isCaptureMove()) {
            capturedPiece.removeFromBoard();
        }
    }

    //undo methods
    public void addCapturedPiece() {
        if (isCaptureMove()) {
            Point addPos = capturedPiece.currentPosition;
            board.pieces[addPos.y][addPos.x] = capturedPiece;
        }
    }

    /* position methods */
    public Point getDistance() {
        return new Point(postponedPosition.x - previousPosition.x, postponedPosition.y - previousPosition.y);
    }

    /* fundamental objekt methods */
    public String toString() {
        return "{movingP: " + movingPiece + "; capturedP: " + capturedPiece + "; previousP: " + previousPosition
                + "; postponedP: " + postponedPosition + "; connected Move: " + connectedMove
                + "; board: " + board + "}";
    }

    public boolean isSimular(Move that) {
        if (that == null) {
            return false;
        }


        //connected move
        boolean connected = true;
        if (this.connectedMove != null && that.connectedMove != null) {
            connected = connectedMove.isSimular(that.connectedMove);
        } else if (this.connectedMove != null || that.connectedMove != null) {
            return false;
        }

        //current move
        if (board.pieceNotSimilar(this.movingPiece, that.movingPiece)) {
            return false;
        }
        if (board.pieceNotSimilar(this.capturedPiece, that.capturedPiece)) {
            return false;
        }
        if (!this.previousPosition.equals(that.previousPosition)) {
            return false;
        }

        return this.postponedPosition.equals(that.postponedPosition);

    }

    //clones everything except board
    public Move clone(Board cloneBoard) {
        try {
            Move clone = (Move) super.clone();

            clone.movingPiece = cloneBoard.getPiece(movingPiece.currentPosition);
            clone.capturedPiece = cloneBoard.getPiece(capturedPiece.currentPosition);
            clone.previousPosition = clone.movingPiece.currentPosition;
            clone.postponedPosition = (Point) postponedPosition.clone();
            clone.connectedMove = connectedMove.clone(cloneBoard);
            clone.board = cloneBoard;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
