package ChessObjects;

import ChessObjects.PieceTypes.Team;
import ChessObjects.Pieces.Pawn;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class BoardTest extends TestCase {

    public BoardTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(BoardTest.class);
    }

    //tests if switching the Team works
    public void testSwitchTeam() {
        Board board = new Board();
        assertSame(board.activePlayer, Team.White);

        board.switchTeam();

        assertNotSame(board.activePlayer, Team.White);
    }

    //tests if positions in and outside the field are correctly classified as in and outside
    public void testIsInsideBoard(){
        Board board = new Board();

        assertTrue(board.isInsideBoard(new Point(0,0)));
        assertTrue(board.isInsideBoard(new Point(7,7)));

        assertFalse(board.isInsideBoard(new Point(-1, 0)));
        assertFalse(board.isInsideBoard(new Point(0, -1)));
        assertFalse(board.isInsideBoard(new Point(8, 0)));
        assertFalse(board.isInsideBoard(new Point(0, 8)));
    }

    //checks if a field is only occupied if a piece is standing on it.
    //it also tests if the bounds check is implemented correctly: outside field = occupied
    public void testIsOccupied(){
        Board board = new Board();
        assertTrue(board.isOccupied(new Point(-1,0)));
        assertFalse(board.isOccupied(new Point(0,0)));

        Pawn pawn = new Pawn(new Point(0,0), Team.White, board);
        pawn.placeOnBoard();
        assertTrue(board.isOccupied(new Point(0,0)));
    }
}
