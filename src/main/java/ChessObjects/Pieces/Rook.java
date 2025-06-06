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
        super("♖", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        //horizontal and vertical moves
        return getDirectionalMoves(Direction.rookMoves, Integer.MAX_VALUE);
    }
}
