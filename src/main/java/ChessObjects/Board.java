package ChessObjects;

import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public Piece [][] pieces;
    public final ArrayList<Move> previousMoves;
    public Team activePlayer;

    public Board(){
        pieces = new Piece[8][8]; //8*8 = chess board size -> [y][x]
        previousMoves = new ArrayList<>();
        activePlayer = Team.White; //white always starts the game
    }

    /* execution methods */
    public void execute(Move move){
        previousMoves.add(move);
        move.execute();
    }

    /* methods to get all moves in a position */
    //getting every confirmed move of a team
    public ArrayList<Move> getMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece [] row : pieces){
            for (Piece piece: row){

                //there has to be a piece on the field, and it needs to be in the active team
                if (piece != null && piece.team.isInSameTeam(activePlayer)){
                    moves.addAll(piece.getMoves());
                }
            }
        }

        return moves;
    }

    //getting every non-confirmed move of a team
    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece [] row : pieces) {
            for (Piece piece : row) {

                //there has to be a piece on the field, and it needs to be in the active team
                if (piece != null && piece.team.isInSameTeam(activePlayer)){
                    moves.addAll(piece.getPossibleMoves());
                }
            }
        }

        return moves;
    }

    /* support methods */
    //switching the active team
    public void switchTeam(){
        if (activePlayer.isInSameTeam(Team.White))
            activePlayer = Team.Black;

        else
            activePlayer = Team.White;
    }

    /*position methods*/
    public boolean isInsideBoard(Point position){
        return position.x >= 0 && position.x < pieces.length && position.y >= 0 && position.y < pieces.length;
    }

    public boolean isOccupied(Point position){
        if (!isInsideBoard(position)){
            return true; //outside field = occupied
        }

        return pieces[position.y][position.x] != null;
    }

    //returns if a position is: outsideBoard = -2, occupied by enemy = -1, free = 0, occupied by teammate = 1
    public int checkout(Point currPos, Direction dir, int distance, Piece checkoutPiece){
        Point checkoutPos = getCheckoutPosition(currPos, dir, distance, checkoutPiece);

        //outside
        if (!isInsideBoard(checkoutPos)){
            return -2;
        }

        //free
        if (pieces[checkoutPos.y][checkoutPos.x] == null){
            return 0;
        }

        //same team
        if (pieces[checkoutPos.y][checkoutPos.x].team.isInSameTeam(checkoutPiece.team)){
            return 1;
        }

        //enemy
        return -1;
    }

    public Point getCheckoutPosition(Point currPos, Direction dir, int distance, Piece checkoutPiece){
        int side = checkoutPiece.team.value;
        return new Point(currPos.x + dir.x * distance * side, currPos.y + dir.y + distance * side);
    }


}
