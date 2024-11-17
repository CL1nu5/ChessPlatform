package ChessObjects;

import ChessObjects.PieceTypes.Direction;
import ChessObjects.PieceTypes.Team;
import ChessObjects.Pieces.Pawn;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class BoardTest extends TestCase {

    public BoardTest(String testName) {
        super(testName);
    }

    public static Test suite() {
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
    public void testIsInsideBoard() {
        Board board = new Board();

        assertTrue(board.isInsideBoard(new Point(0, 0)));
        assertTrue(board.isInsideBoard(new Point(7, 7)));

        assertFalse(board.isInsideBoard(new Point(-1, 0)));
        assertFalse(board.isInsideBoard(new Point(0, -1)));
        assertFalse(board.isInsideBoard(new Point(8, 0)));
        assertFalse(board.isInsideBoard(new Point(0, 8)));
    }

    //checks if a field is only occupied if a piece is standing on it.
    //it also tests if the bounds check is implemented correctly: outside field = occupied
    public void testIsOccupied() {
        Board board = new Board();
        assertTrue(board.isOccupied(new Point(-1, 0)));
        assertFalse(board.isOccupied(new Point(0, 0)));

        Pawn pawn = new Pawn(new Point(0, 0), Team.White, board);
        pawn.placeOnBoard();
        assertTrue(board.isOccupied(new Point(0, 0)));
    }

    //tests checking out position is correct for black and white pieces
    public void testGetCheckoutPosition() {
        Board board = new Board();

        Pawn piece1 = new Pawn(new Point(4, 4), Team.White, board);
        assertEquals(new Point(3, 3), board.getCheckoutPosition(Direction.Up_Left, 1, piece1));
        assertEquals(new Point(2, 2), board.getCheckoutPosition(Direction.Up_Left, 2, piece1));
        assertEquals(new Point(1, 4), board.getCheckoutPosition(Direction.Left, 3, piece1));

        Pawn piece2 = new Pawn(new Point(3, 3), Team.Black, board);
        assertEquals(new Point(5, 5), board.getCheckoutPosition(Direction.Up_Left, 2, piece2));
        assertEquals(new Point(3, 7), board.getCheckoutPosition(Direction.Up, 4, piece2));
        assertEquals(new Point(1, 1), board.getCheckoutPosition(Direction.Down_Right, 2, piece2));
    }

    //testing every checkout szenario: outside, free, enemy, teammate
    public void testCheckout() {
        Board board = new Board();

        Pawn piece1 = new Pawn(new Point(0, 7), Team.White, board);
        Pawn piece2 = new Pawn(new Point(0, 6), Team.White, board);
        piece2.placeOnBoard();
        Pawn piece3 = new Pawn(new Point(1, 6), Team.Black, board);
        piece3.placeOnBoard();

        assertEquals(-2, board.checkout(Direction.Left, 1, piece1));          //outside
        assertEquals(0, board.checkout(Direction.Up, 2, piece1));             //free
        assertEquals(1, board.checkout(Direction.Up, 1, piece1));             //teammate
        assertEquals(-1, board.checkout(Direction.Up_Right, 1, piece1));      //enemy
    }

    public void testSimular() {
        Board board1 = new Board();
        Board board2 = new Board();

        //reference shouldn't be important, position, past moves and active teams are the only important parameters
        assertTrue(board1.isSimilar(board2));

        //there should be a difference because of the placed piece
        Pawn pawn1 = new Pawn(new Point(0, 0), Team.White, board1);
        pawn1.placeOnBoard();
        assertFalse(board1.isSimilar(board2));

        //after adding the second piece the boars should be equal
        Pawn pawn2 = new Pawn(new Point(0, 0), Team.White, board2);
        pawn2.placeOnBoard();
        assertTrue(board1.isSimilar(board2));

        //after moving hte pawn around the boards aren't equal anymore
        board2.executeMove(new Move(pawn2, new Point(0, 5)));
        board2.executeMove(new Move(pawn2, new Point(0, 0)));
        assertFalse(board1.isSimilar(board2));
    }

    public void testClone(){
        //setup
        Board board1 = new Board();
        Piece pawn = new Pawn(new Point(0,0),Team.Black, board1);
        pawn.placeOnBoard();
        Board board2 = board1.clone();

        //checks if similar after all
        assertTrue(board1.isSimilar(board2));

        //checks that everything is different after cloning
        assertNotSame(board1, board2);
        assertNotSame(board1.pieces, board2.pieces);
        assertNotSame(board1.previousMoves, board2.previousMoves);
        assertNotSame(board1.pieces[0][0], board2.pieces[0][0]);

        board1.executeMove(pawn.getPossibleMoves().get(0));
        assertNotSame(board1.previousMoves.size(),board2.previousMoves.size());
        assertFalse(board1.isSimilar(board2));
    }

    public void testExecuteAndUndo(){
        //setup
        Board board1 = new Board();
        Piece pawn = new Pawn(new Point(0,0),Team.Black, board1);
        pawn.placeOnBoard();
        Board board2 = board1.clone();

        Move move = pawn.getPossibleMoves().get(1);

        //checks if they change after execution
        board1.executeMove(move);
        assertFalse(board1.isSimilar(board2));

        //checks if they are same again after undo
        board1.undoMove(move);
        assertTrue(board1.isSimilar(board2));

    }
}
