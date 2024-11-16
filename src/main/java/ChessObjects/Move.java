package ChessObjects;

import ChessObjects.Pieces.King;

import java.awt.*;
import java.util.logging.Logger;

public class Move {
    public final Piece movingPiece, capturedPiece;
    public final Point previousPosition, postponedPosition;
    public final Move connectedMove;
    public final Board board;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //general constructor
    public Move(Piece movingPiece, Piece capturedPiece, Point postponedPosition, Move connectedMove, Board board) {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.previousPosition = movingPiece.currentPosition;
        this.postponedPosition = postponedPosition;
        this.connectedMove = connectedMove;
        this.board = board;
    }

    //constructor for basic moves (non capture)
    public Move(Piece movingPiece, Point postponedPosition, Board board){
        this(movingPiece, null, postponedPosition, null, board);
    }

    /* capture methods */
    //checks if a piece was captured by this move
    public boolean isCaptureMove(){
        return capturedPiece != null;
    }

    public boolean isKingCaptured(){
        if(! isCaptureMove()){
            return false;
        }
        return capturedPiece instanceof King;
    }

    /* updating moves */
    public void execute(){
        removeCapturedPiece();

        if(!movingPiece.setPosition(postponedPosition)){
            logger.warning("execute - position update not possible");
        }

        if (connectedMove != null){
            connectedMove.execute();
        }
    }

    public void undo(){
        if (connectedMove != null){
            connectedMove.undo();
        }

        if (!movingPiece.setPosition(previousPosition)){
            logger.warning("undo - position update not possible");
        }

        addCapturedPiece();
    }

    //execution methods
    public void removeCapturedPiece(){
        if (isCaptureMove()) {
            capturedPiece.removeFromBoard();
        }
    }

    //undo methods
    public void addCapturedPiece(){
        if (isCaptureMove()){
            Point addPos = capturedPiece.currentPosition;
            board.pieces[addPos.y][addPos.x] = capturedPiece;
        }
    }

    /* position methods */
    public Point getDistance(){
        return new Point(postponedPosition.x - previousPosition.x, postponedPosition.y - previousPosition.y);
    }

    /* fundamental objekt methods */
    public String toString(){
        return "{movingP: " + movingPiece +"; capturedP: " + capturedPiece + "; previousP: " + previousPosition
                + "; postponedP: " + postponedPosition + "; connected Move: " + connectedMove
                + "; board: " + board +"}";
    }
}
