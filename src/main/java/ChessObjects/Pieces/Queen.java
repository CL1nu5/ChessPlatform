package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(Point startingPosition, Team team, Board board) {
        super(startingPosition, team, board);
    }

    @Override
    public ArrayList<Move> getPossibleMoves() {
        return null;
    }
}
