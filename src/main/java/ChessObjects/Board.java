package ChessObjects;

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

    /* methods to get all moves in a position */
    //getting every confirmed move of a team
    public ArrayList<Move> getMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece [] row : pieces){
            for (Piece piece: row){

                //there has to be a piece on the field, and it needs to be in the active team
                if (piece != null && piece.team.isEqual(activePlayer)){
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
                if (piece != null && piece.team.isEqual(activePlayer)){
                    moves.addAll(piece.getPossibleMoves());
                }
            }
        }

        return moves;
    }

    /* support methods */
    //switching the active team
    public void switchTeam(){
        if (activePlayer.isEqual(Team.White))
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
}
