package ChessObjects;

import ChessObjects.Pieces.King;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.awt.*;

public class Move {
    private final Piece movingPiece, capturedPiece;
    private final Point previousPosition, postponedPosition;
    private final Move connectedMove;
    private final Board board;

    private static final Logger logger = (Logger) LogManager.getLogger(Move.class);

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
        //if there are no more connected moves the team should be switched back
        if (connectedMove == null){
            board.switchTeam();
        }

        removeCapturedPiece();

        if(!movingPiece.setPosition(postponedPosition)){
            logger.error("execute - position update not possible");
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
            logger.error("undo - position update not possible");
        }

        addCapturedPiece();

        //if there are no more connected moves the team should be switched back
        if (connectedMove == null){
            board.switchTeam();
        }
    }

    //execution methods
    public void removeCapturedPiece(){
        if (isCaptureMove()) {
            Point removePos = capturedPiece.currentPosition;
            board.pieces[removePos.y][removePos.x] = null;
        }
    }

    //undo methods
    public void addCapturedPiece(){
        if (isCaptureMove()){
            Point addPos = capturedPiece.currentPosition;
            board.pieces[addPos.y][addPos.x] = capturedPiece;
        }
    }
}
