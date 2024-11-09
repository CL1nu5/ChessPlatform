package ChessObjects;

import java.awt.*;

public class Move {
    private final Piece movingPiece, capturedPiece;
    private final Point previousPosition, postponedPosition;
    private final Move connectedMove;
    private final Board board;

    //general constructor
    public Move(Piece movingPiece, Piece capturedPiece, Point postponedPosition, Move connectedMove, Board board) {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.previousPosition = movingPiece.currentPosition;
        this.postponedPosition = postponedPosition;
        this.connectedMove = connectedMove;
        this.board = board;
    }

    //constructor for basic moves
    public Move(Piece movingPiece, Point postponedPosition, Board board){
        this(movingPiece, null, postponedPosition, null, board);
    }

    //checks if a piece was captured by this move
    public boolean isCaptureMove(){
        return capturedPiece != null;
    }

    public void execute(){
        removeCapturedPiece();
        movingPiece.setPosition(postponedPosition);

        if (connectedMove != null){
            connectedMove.execute();
        }
    }

    public void undo(){
        //Todo
    }

    //execution methods
    public void removeCapturedPiece(){
        if (isCaptureMove()) {
            Point removePos = capturedPiece.currentPosition;
            board.pieces[removePos.y][removePos.x] = null;
        }
    }
}
