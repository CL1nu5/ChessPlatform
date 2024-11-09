package ChessObjects;

import java.util.ArrayList;

public class Board {
    private Piece [][] pieces;
    private ArrayList<Move> previousMoves;

    public Board(){
        pieces = new Piece[8][8]; //8*8 = chess board size -> [y][x]
        previousMoves = new ArrayList<>();
    }


}
