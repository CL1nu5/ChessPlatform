package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Point startingPosition, Team team, Board board) {
        super("Kn", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        //Knight Directions (L-shaped moves)
        return getDirectionalMoves(Direction.knightMoves, 1);
    }
}
