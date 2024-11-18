package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(Point startingPosition, Team team, Board board) {
        super("K", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>(getDirectionalMoves(Direction.queenMoves, 1));
        castle(moves);

        return moves;
    }

    public void castle(ArrayList<Move> moves){
        //it has to be the first move of the king
        if (!getPreviousMoves().isEmpty()){
            return;
        }

        //set king to other team, so he can capture allay rook
        team = team.getOpposite();

        //check if there are no pieces between the king and the rooks
        Direction[] directions = {Direction.Left, Direction.Right};
        ArrayList<Move> captures = getCaptureMoves(getDirectionalMoves(directions, Integer.MAX_VALUE));

        //switch back after finding rooks
        team = team.getOpposite();


        for (Move capture : captures){
            if (capture.capturedPiece instanceof Rook){
                //rooks to perform castle with
                Rook rook = (Rook) capture.capturedPiece;

                //check that rook hasn't performed any moves before
                if (!rook.getPreviousMoves().isEmpty())
                    continue;

                //check if king is moving through check
                Direction dir = Direction.getDirectionByDistance(currentPosition, rook.currentPosition);
                Move move = new Move(this, getCheckoutPosition(dir, 1));
                if (move.isIllegalMove()){
                    continue;
                }

                //move is legal and added
                Move rookMove = new Move(rook, getCheckoutPosition(dir, 1));
                moves.add(new Move(this, null, getCheckoutPosition(dir, 2), rookMove));
            }
        }
    }
}
