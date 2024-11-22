package ChessObjects;

import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Board implements Cloneable{
    public Piece[][] pieces;
    public ArrayList<Move> previousMoves;
    public Team activePlayer;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Board() {
        pieces = new Piece[8][8]; //8*8 = chess board size -> [y][x]
        previousMoves = new ArrayList<>();
        activePlayer = Team.White; //white always starts the game
    }

    /* execution methods */
    public void executeMove(Move move) {
        move.execute();
        previousMoves.add(move);
        switchTeam();
    }

    public void undoMove(Move move) {
        switchTeam();
        previousMoves.remove(move);
        move.undo();
    }

    /* methods to get all moves in a position */
    //getting every confirmed move of a team
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece[] row : pieces) {
            for (Piece piece : row) {

                //there has to be a piece on the field, and it needs to be in the active team
                if (piece != null && piece.team.isInSameTeam(activePlayer)) {
                    moves.addAll(piece.getMoves());
                }
            }
        }

        return moves;
    }

    //getting every non-confirmed move of a team
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece[] row : pieces) {
            for (Piece piece : row) {

                //there has to be a piece on the field, and it needs to be in the active team
                if (piece != null && piece.team.isInSameTeam(activePlayer)) {
                    moves.addAll(piece.getPossibleMoves());
                }
            }
        }

        return moves;
    }

    /* support methods */
    //switching the active team
    public void switchTeam() {
        activePlayer = activePlayer.getOpposite();
    }

    /*position methods*/
    public boolean isInsideBoard(Point position) {
        return position.x >= 0 && position.x < pieces.length && position.y >= 0 && position.y < pieces.length;
    }

    public boolean isOccupied(Point position) {
        if (!isInsideBoard(position)) {
            return true; //outside field = occupied
        }

        return pieces[position.y][position.x] != null;
    }

    //returns if a position is: outsideBoard = -2, occupied by enemy = -1, free = 0, occupied by teammate = 1
    public int checkout(Direction dir, int distance, Piece checkoutPiece) {
        return checkout(getCheckoutPosition(dir, distance, checkoutPiece), checkoutPiece);
    }

    public int checkout(Point checkoutPos, Piece checkoutPiece) {
        //outside
        if (!isInsideBoard(checkoutPos)) {
            return -2;
        }

        //free
        if (pieces[checkoutPos.y][checkoutPos.x] == null) {
            return 0;
        }

        //same team
        if (pieces[checkoutPos.y][checkoutPos.x].team.isInSameTeam(checkoutPiece.team)) {
            return 1;
        }

        //enemy
        return -1;
    }

    public Point getCheckoutPosition(Direction dir, int distance, Piece checkoutPiece) {
        int side = checkoutPiece.team.value;
        Point currPos = checkoutPiece.currentPosition;
        return new Point(currPos.x + dir.x * distance * side, currPos.y + dir.y * distance * side);
    }

    /* information methods / getter */
    public Piece getPiece(Point position) {
        return pieces[position.y][position.x];
    }

    public Move getLastMove() {
        if (previousMoves.isEmpty()) {
            return null;
        }

        return previousMoves.get(previousMoves.size() - 1);
    }

    /* save and load options */


    /* fundamental objekt methods */
    public boolean isSimilar(Board that) {
        if (that == null) {
            return false;
        }

        if (!arePiecesSimilar(that)) {
            return false;
        }

        if (this.activePlayer.isEnemy(that.activePlayer)) {
            return false;
        }

        return this.previousMoves.equals(that.previousMoves);

    }

    public boolean arePiecesSimilar(Board that) {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieceNotSimilar(this.pieces[i][j], that.pieces[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean pieceNotSimilar(Piece p1, Piece p2) {
        if (p1 == null && p2 == null) {
            return false;
        }

        if (p1 == null || p2 == null) {
            return true;
        }

        return p1.isSimular(p2);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("----------------------Position-----------------------" + '\n');
        for (Piece[] row : pieces) {
            for (Piece piece : row) {
                if (piece == null)
                    s.append(" ,");
                else
                    s.append(piece.displayCharacter).append(",");
            }
            s.append('\n');
        }

        s.append("---------------------ActiveTeam----------------------" + '\n');
        s.append(activePlayer.toString()).append("\n");

        s.append("-------------------Previous-Moves--------------------" + '\n');
        for (Move move : previousMoves) {
            s.append(move.toString()).append('\n');
        }

        return s.toString();
    }

    //clones ever piece and move.
    @Override
    @SuppressWarnings("unchecked")
    public Board clone() {
        try {
            Board clone = (Board) super.clone();

            clone.pieces = new Piece[8][8];
            clone.previousMoves = (ArrayList<Move>) previousMoves.clone();

            clonePieces(clone);
            clonePreviousMoves(clone);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void clonePieces(Board clone){
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] != null){
                    clone.pieces[i][j] = pieces[i][j].clone(clone);
                }
            }
        }
    }

    public void clonePreviousMoves(Board clone){
        for (int i = 0; i < previousMoves.size(); i++){
            clone.previousMoves.add(i, previousMoves.get(i).clone(clone));
        }
    }
}