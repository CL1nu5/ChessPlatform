package ChessObjects;

import java.awt.*;

public class Move {
    private final Piece movingPiece, capturedPiece;
    private final Point previousPosition, postponedPosition;
    private final Move connectedMove;

    //general constructor
    public Move(Piece movingPiece, Piece capturedPiece, Point previousPosition, Point postponedPosition, Move connectedMove) {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.previousPosition = previousPosition;
        this.postponedPosition = postponedPosition;
        this.connectedMove = connectedMove;
    }

    //constructor for basic moves
    public Move(Piece movingPiece, Point previousPosition, Point postponedPosition){
        this(movingPiece, null, previousPosition, postponedPosition, null);
    }

    //checks if a piece was captured by this move
    public boolean isCaptureMove(){
        return capturedPiece != null;
    }

    public void execute(){
        //Todo
    }

    public void undo(){
        //Todo
    }
}
