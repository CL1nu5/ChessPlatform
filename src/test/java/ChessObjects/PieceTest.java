package ChessObjects;

import ChessObjects.PieceTypes.Team;
import ChessObjects.Pieces.Pawn;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;


public class PieceTest extends TestCase {

    public PieceTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PieceTest.class);
    }

    //Tests if the piece shows up on the board after placing it
    public void testPlaceOnBoard() {
        Board board = new Board();
        assertNull(board.pieces[0][0]);

        Piece piece = new Pawn(new Point(0, 0), Team.White, board);
        assertNull(board.pieces[0][0]);

        assertTrue(piece.placeOnBoard());
        assertNotNull(board.pieces[0][0]);
    }

    /* tests 3 different cases:
     * - 1: piece can move inside the field
     * - 2: piece can't move outside field
     * - 3: the piece really moved to the new field in case 2 (7,7), so the new piece can't move there
     */
    public void testSetPosition() {
        Board board = new Board();

        Piece piece1 = new Pawn(new Point(0, 0), Team.White, board);
        piece1.placeOnBoard();

        assertTrue(piece1.setPosition(new Point(7, 7)));
        assertFalse(piece1.setPosition(new Point(-1, 0)));

        Piece piece2 = new Pawn(new Point(0, 0), Team.Black, board);
        piece2.placeOnBoard();

        assertFalse(piece2.setPosition(new Point(7, 7)));
    }

}
