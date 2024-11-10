package ChessObjects;

import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    protected Point currentPosition;
    protected final Board board;
    protected Team team;

    public Piece(Point startingPosition, Team team, Board board){
        this.currentPosition = startingPosition;
        this.team = team;
        this.board = board;
    }

    public boolean placeOnBoard(){
        if (board.isOccupied(currentPosition)){
            return false;
        }

        board.pieces[currentPosition.y][currentPosition.x] = this;
        return true;
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

    //confirmed Moves
    public ArrayList<Move> getMoves(){
        ArrayList<Move> moves = getPossibleMoves();

        for (int i = 0; i < moves.size(); i++){
            Move move = moves.get(i);

            move.execute();

            ArrayList<Move> counterMoves = board.getPossibleMoves();
            //check if any counter move captures the king -> non-legal move
            for (Move counterMove : counterMoves){
                if (counterMove.isKingCaptured()){
                    moves.remove(i);
                    i--;
                    break;
                }
            }
        }

        return moves;
    }

    //non confirmed Moves
    public abstract ArrayList<Move> getPossibleMoves();
}
