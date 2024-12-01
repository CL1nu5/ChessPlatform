package ChessObjects;

import ChessObjects.PieceTypes.Team;
import ChessObjects.Pieces.Pawn;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class MoveTest extends TestCase {
    public MoveTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MoveTest.class);
    }

    public void testGetDistance() {
        //setup
        Board board = new Board();
        Pawn piece = new Pawn(new Point(1, 1), Team.White, board);

        Move move1 = new Move(piece, new Point(2, 2));
        Move move2 = new Move(piece, new Point(5, 3));
        Move move3 = new Move(piece, new Point(1, 1));
        Move move4 = new Move(piece, new Point(0, 7));

        //test
        assertEquals(move1.getDistance(), new Point(1, 1));
        assertEquals(move2.getDistance(), new Point(4, 2));
        assertEquals(move3.getDistance(), new Point(0, 0));
        assertEquals(move4.getDistance(), new Point(-1, 6));
    }

    public void testSimilar(){
        //setup
        Board board = new Board();

        Pawn piece = new Pawn(new Point(1, 1), Team.White, board);
        piece.placeOnBoard();

        Move move1 = new Move(piece, new Point(2, 5));
        Move move2 = new Move(piece, new Point(2, 5));

        assertTrue(move1.isSimular(move2));
    }

    public void testClone(){
        //setup
        Board board = new Board();
        Board cloneBoard = new Board();

        Pawn piece1 = new Pawn(new Point(1, 1), Team.White, board);
        Pawn piece2 = new Pawn(new Point(1, 1), Team.White, cloneBoard);

        piece1.placeOnBoard();
        piece2.placeOnBoard();

        Move moveOriginal = new Move(piece1, new Point(2, 2));

        //test move equals itself
        assertEquals(moveOriginal, moveOriginal);

        //test it doesn't equal itself after cloning
        Move newMove = moveOriginal.clone(cloneBoard);
        assertFalse(moveOriginal.equals(newMove));

        //but move is still similar
        assertTrue(moveOriginal.isSimular(newMove));
    }
}
