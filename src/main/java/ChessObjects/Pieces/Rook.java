package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(Point startingPosition, Team team, Board board) {
        super("R", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        Direction[] directions = new Direction[]{Direction.Up, Direction.Down, Direction.Left, Direction.Right};
        return getDirectionalMoves(directions, Integer.MAX_VALUE);
    }
}
