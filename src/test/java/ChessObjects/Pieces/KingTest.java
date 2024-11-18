package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class KingTest extends TestCase {
    public KingTest(String testName){
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(KingTest.class);
    }

    public void testPossibleMoves(){
        //setup
        Board board = new Board();
        King king = new King(new Point(3,3), Team.White, board);
        king.placeOnBoard();

        //in the middle of the board the king should be able to access all surrounding fields
        assertEquals(8, king.getPossibleMoves().size());

        //on the edge of the board the king can't move outside
        king.setPosition(new Point(4, 7));
        assertEquals(5, king.getPossibleMoves().size());

        //is blocked by teammate (one move less)
        Pawn teammate = new Pawn(new Point(3,7), Team.White, board);
        teammate.placeOnBoard();
        assertEquals(4, king.getPossibleMoves().size());

        //isn't blocked by enemy -> moves don't change
        Pawn enemy = new Pawn(new Point(5,7), Team.Black, board);
        enemy.placeOnBoard();
        assertEquals(4, king.getPossibleMoves().size());
    }

    public void testCastle(){
        //setup
        Board board = new Board();
        King king = new King(new Point(4,7), Team.White, board);
        king.placeOnBoard();

        //status before castle is possible
        assertEquals(5, king.getPossibleMoves().size());

        //add rook so now castle is possible
        Rook rook = new Rook(new Point(0, 7), Team.White, board);
        rook.placeOnBoard();
        assertEquals(6, king.getPossibleMoves().size());

        //set a teammate between, to make castle impossible
        Pawn teammate = new Pawn(new Point(2,7), Team.White, board);
        teammate.placeOnBoard();
        assertEquals(5, king.getPossibleMoves().size());
        teammate.removeFromBoard();

        //between validation
        assertEquals(6, king.getPossibleMoves().size());

        //set an enemy between, to make castle impossible
        Pawn enemy = new Pawn(new Point(2,7), Team.Black, board);
        enemy.placeOnBoard();
        assertEquals(5, king.getPossibleMoves().size());
        enemy.removeFromBoard();

        //between validation
        assertEquals(6, king.getPossibleMoves().size());

        //check that king cant move through check (other two moves are possible,
        //because check for end position isn't done in this step
        Rook enemyRook = new Rook(new Point(3, 0), Team.Black, board);
        enemyRook.placeOnBoard();
        assertEquals(5, king.getPossibleMoves().size());
        enemyRook.removeFromBoard();

        //move king to make castle impossible
        Move move = new Move(king, new Point(5, 7));
        board.executeMove(move);
        assertEquals(5, king.getPossibleMoves().size());
        board.undoMove(move);

        //between validation
        assertEquals(6, king.getPossibleMoves().size());

        //move rook, so castle is impossible
        board.executeMove(new Move(rook, new Point(1, 7)));
        board.executeMove(new Move(rook, new Point(0, 7)));
        assertEquals(5, king.getPossibleMoves().size());
    }
}
