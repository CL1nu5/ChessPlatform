package ChessObjects;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public Piece [][] pieces;
    private ArrayList<Move> previousMoves;

    public Board(){
        pieces = new Piece[8][8]; //8*8 = chess board size -> [y][x]
        previousMoves = new ArrayList<>();
    }

    //position methods
    public boolean isInsideBoard(Point position){
        return position.x >= 0 && position.x < pieces.length && position.y >= 0 && position.y < pieces.length;
    }

    public boolean isOccupied(Point position){
        if (!isInsideBoard(position)){
            return false;
        }

        return pieces[position.y][position.x] != null;
    }
}
