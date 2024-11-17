package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(Point startingPosition, Team team, Board board) {
        super("K", startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        return null;
    }
}
