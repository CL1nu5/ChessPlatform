package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class RookTest extends TestCase {

    public RookTest (String testName){
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RookTest.class);
    }

    public void testPossibleMoves(){
        //setup
        Board board = new Board();
        Piece rook = new Rook(new Point(4,4), Team.White, board);
        rook.placeOnBoard();

        //test all files are open
        assertEquals(14, rook.getPossibleMoves().size());

        //tests if enemy can be captured
        Piece enemy1 = new Pawn(new Point(4, 6), Team.Black, board);
        enemy1.placeOnBoard();
        assertEquals(13, rook.getPossibleMoves().size());
        assertEquals(1, rook.getCaptureMoves().size());

        Piece enemy2 = new Pawn(new Point(2, 4), Team.Black, board);
        enemy2.placeOnBoard();
        assertEquals(11, rook.getPossibleMoves().size());
        assertEquals(2, rook.getCaptureMoves().size());

        //test if teammate cant be captured and blockes file
        Piece teammate = new Pawn(new Point(3, 4), Team.White, board);
        teammate.placeOnBoard();
        assertEquals(9, rook.getPossibleMoves().size());
        assertEquals(1, rook.getCaptureMoves().size());
    }
}
