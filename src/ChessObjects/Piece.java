package ChessObjects;

import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Piece implements Cloneable {
    protected Board board;
    public Point currentPosition;
    public Team team;
    public String displayCharacter;

    public Piece(String displayCharacter, Point startingPosition, Team team, Board board) {
        this.currentPosition = startingPosition;
        this.team = team;
        this.board = board;
        this.displayCharacter = displayCharacter;
    }

    /* getting moves */
    //get confirmed Moves of this piece
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = getPossibleMoves();

        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).isIllegalMove()){
                moves.remove(i);
                i--;
            }
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

    //non confirmed moves
    public abstract ArrayList<Move> getPossibleMoves();

    //get every move that captures an opponent piece
    public ArrayList<Move> getCaptureMoves(){
        return getCaptureMoves(getPossibleMoves());
    }

    public ArrayList<Move> getCaptureMoves(ArrayList<Move> moves){
        return (ArrayList<Move>) moves.stream().filter(Move::isCaptureMove).collect(Collectors.toList());
    }

    //getting directional moves to a preset maximum depth or the end of the board
    public ArrayList<Move> getDirectionalMoves(Direction[] directions, int maxDepth) {
        ArrayList<Move> moves = new ArrayList<>();

        //check edge case
        if (maxDepth == 0) {
            return moves;
        }

        //go through every direction
        for (Direction dir : directions) {
            int depth = 1;

            do {
                Point checkoutPos = getCheckoutPosition(dir, depth);
                int evaluation = checkout(checkoutPos);

                //outside and teammate
                if (evaluation == -2 || evaluation == 1) {
                    break;
                }

                //enemy
                else if (evaluation == -1) {
                    moves.add(new Move(this, board.getPiece(checkoutPos), checkoutPos, null));
                    break;
                }

                //free
                else {
                    moves.add(new Move(this, checkoutPos));
                }

                depth++;
            } while (depth < maxDepth);
        }

        return moves;
    }


    /* position methods */
    //makes board checkout notation shorter for each piece
    public int checkout(Point checkoutPosition) {
        return board.checkout(checkoutPosition, this);
    }

    public Point getCheckoutPosition(Direction direction, int distance) {
        return board.getCheckoutPosition(direction, distance, this);
    }

    //adds the board to the selected board, before this method is called it won't apear
    public boolean placeOnBoard() {
        if (board.isOccupied(currentPosition)) {
            return false;
        }

        board.pieces[currentPosition.y][currentPosition.x] = this;
        return true;
    }

    //removes the piece from the current board position
    public void removeFromBoard() {
        if (currentPosition != null) {
            board.pieces[currentPosition.y][currentPosition.x] = null;
        }
    }

    //relocates
    public boolean setPosition(Point newPosition) {
        //check if position is inside board and position is occupied by enemy
        if (board.isOccupied(newPosition)) {
            if (board.checkout(newPosition, this) < 0){
                return false;
            }
        }

        //changing the board postion of the piece
        removeFromBoard();
        this.currentPosition = newPosition;
        placeOnBoard();

        return true;
    }

    /* fundamental objekt methods */
    public String toString() {
        return "{" + displayCharacter + ";" + team + ";[x=" + currentPosition.x + ",y=" + currentPosition.y + "]}";
    }

    public boolean isSimular(Piece that) {
        if (that == null) {
            return false;
        }


        if (!this.currentPosition.equals(that.currentPosition)) {
            return false;
        }

        if (this.team.isEnemy(that.team)) {
            return false;
        }

        return this.displayCharacter.equals(that.displayCharacter);
    }

    //clones everything
    public Piece clone(Board cloneBoard) {
        try {
            Piece clone = (Piece) super.clone();

            clone.currentPosition = (Point) currentPosition.clone();
            clone.board = cloneBoard;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
