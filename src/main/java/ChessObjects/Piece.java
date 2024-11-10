package ChessObjects;

import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Piece {
    protected Point currentPosition;
    protected final Board board;
    protected Team team;
    protected String displayCharacter;

    public Piece(String displayCharacter, Point startingPosition, Team team, Board board){
        this.currentPosition = startingPosition;
        this.team = team;
        this.board = board;
        this.displayCharacter = displayCharacter;
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

        //changing the board postion of the piece
        board.pieces[currentPosition.y][currentPosition.x] = null;
        board.pieces[newPosition.y][newPosition.x] = this;

        this.currentPosition = newPosition;
        return true;
    }

    //get confirmed Moves of this piece
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

    //get every move performed by this piece
    public ArrayList<Move> getPreviousMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        for (Move previousMove : board.previousMoves){
            if (previousMove.movingPiece == this){
                moves.add(previousMove);
            }
        }

        return moves;
    }

    //non confirmed Moves
    public abstract ArrayList<Move> getPossibleMoves();

    /* fundamental objekt methods */
    public String toString(){
        return  "{" + displayCharacter +";" + team +  ";[y=" + currentPosition.y + ",x=" + currentPosition.x + "]}";
    }
}
