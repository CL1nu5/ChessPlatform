package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Point startingPosition, Team team, Board board) {
        super("P", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        //move one step forward
        if(board.checkout(Direction.Up,1, this) == 0){

        }


        return moves;
    }
}
