package ChessObjects;

import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    protected final Board board;
    protected Point currentPosition;
    protected Team team;
    protected String displayCharacter;

    public Piece(String displayCharacter, Point startingPosition, Team team, Board board) {
        this.currentPosition = startingPosition;
        this.team = team;
        this.board = board;
        this.displayCharacter = displayCharacter;
    }

    public boolean placeOnBoard() {
        if (board.isOccupied(currentPosition)) {
            return false;
        }

        board.pieces[currentPosition.y][currentPosition.x] = this;
        return true;
    }

    public void removeFromBoard() {
        if (currentPosition != null) {
            board.pieces[currentPosition.y][currentPosition.x] = null;
        }
    }

    public boolean setPosition(Point newPosition) {
        //check if position is inside board and position is occupied
        if (board.isOccupied(newPosition)) {
            return false;
        }

        //changing the board postion of the piece
        board.pieces[currentPosition.y][currentPosition.x] = null;
        board.pieces[newPosition.y][newPosition.x] = this;

        this.currentPosition = newPosition;
        return true;
    }

    //get confirmed Moves of this piece
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = getPossibleMoves();

        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);

            board.executeMove(move);

            ArrayList<Move> counterMoves = board.getPossibleMoves();
            //check if any counter move captures the king -> non-legal move
            for (Move counterMove : counterMoves) {
                if (counterMove.isKingCaptured()) {
                    moves.remove(i);
                    i--;
                    break;
                }
            }

            board.undoMove(move);
        }

        return moves;
    }

    //get every move performed by this piece
    public ArrayList<Move> getPreviousMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (Move previousMove : board.previousMoves) {
            if (previousMove.movingPiece == this) {
                moves.add(previousMove);
            }
        }

        return moves;
    }

    //makes board checkout notation shorter for each piece
    public int checkout(Point checkoutPosition) {
        return board.checkout(checkoutPosition, this);
    }

    public Point getCheckoutPosition(Direction direction, int distance) {
        return board.getCheckoutPosition(direction, distance, this);
    }

    //non confirmed Moves
    public abstract ArrayList<Move> getPossibleMoves();

    /* fundamental objekt methods */
    public String toString() {
        return "{" + displayCharacter + ";" + team + ";[y=" + currentPosition.y + ",x=" + currentPosition.x + "]}";
    }

    public boolean equals(Object that) {
        if (that instanceof Piece){
            Piece other = (Piece) that;

            if (this.currentPosition != other.currentPosition){
                return false;
            }

            if (this.team.isEnemy(other.team)){
                return false;
            }

            return this.displayCharacter.equals(other.displayCharacter);
        }

        return false;
    }
}
