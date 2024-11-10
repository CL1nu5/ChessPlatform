package ChessObjects;

import java.awt.*;

public abstract class Piece {
    protected Point currentPosition;
    private final Board board;

    Piece(Point startingPosition, Board board){
        this.currentPosition = startingPosition;
        this.board = board;
    }

    public boolean setPosition(Point newPosition){
        //check if position is inside board and position is occupied
        if (board.isOccupied(newPosition)){
            return false;
        }

        //changing the board postion of the pies
        board.pieces[currentPosition.y][currentPosition.x] = null;
        board.pieces[newPosition.y][newPosition.x] = this;

        this.currentPosition = newPosition;
        return true;
    }

}
