package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(Point startingPosition, Team team, Board board) {
        super("Q", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        //horizontal, vertical and diagonal moves
        return getDirectionalMoves(Direction.queenMoves, Integer.MAX_VALUE);
    }
}
