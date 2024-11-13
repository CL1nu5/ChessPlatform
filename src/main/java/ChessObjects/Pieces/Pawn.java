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

        if (moveOneStep(moves)){
            moveTwoSteps(moves);
        }

        capture(moves);
        enPassant(moves);

        return moves;
    }

    //move single step forward, returns: "true" if move is possible, else -> "false"
    public boolean moveOneStep(ArrayList<Move> moves){
        Point forwardPosition = getCheckoutPosition(Direction.Up, 1);

        if (checkout(forwardPosition) == 0) {
            moves.add(new Move(this, forwardPosition, board));
            return true;
        }

        return false;
    }

    //move two steps forward this method asserts, that single step forward is possible
    public void moveTwoSteps(ArrayList<Move> moves){
        if (getPreviousMoves().isEmpty()) {
            Point forwardPosition = getCheckoutPosition(Direction.Up, 2);

            if (checkout(forwardPosition) == 0){
                moves.add(new Move(this, forwardPosition, board));
            }
        }
    }

    //moves single step forward + to the right/left if it can capture a piece there
    public void capture(ArrayList<Move> moves){
        for (Direction dir: new Direction[]{Direction.Up_Left, Direction.Up_Right}){
            Point capturePosition = getCheckoutPosition(dir, 1);

            if (checkout(capturePosition) == -1){
                moves.add(new Move(this, board.getPiece(capturePosition), capturePosition, null, board));
            }
        }
    }

    //captures a pawn, trying to bypass by moving two steps in the last move, directly next to it
    public void enPassant(ArrayList<Move> moves){

    }
}
