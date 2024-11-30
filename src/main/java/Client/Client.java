package Client;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;

import java.util.ArrayList;

public class Client {

    private Board board;

    public Client(Board board){
        this.board = board;
    }

    public ArrayList<Move> getPossibleMoves(Piece piece){
        return piece.getMoves();
    }

    public boolean executeMove(Move move){
        board.executeMove(move);
        return true;
    }
}
