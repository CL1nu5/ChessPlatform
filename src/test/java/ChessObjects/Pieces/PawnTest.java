package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class PawnTest extends TestCase {

    public PawnTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PawnTest.class);
    }

    public void testSingleAndDoubleMoves() {
        //setup
        Board board = new Board();
        Piece pawn = new Pawn(new Point(0, 7), Team.White, board);

        //double and single move work at the beginning
        pawn.placeOnBoard();
        assertEquals(2, pawn.getPossibleMoves().size());

        //double move doesn't work anymore, because now a piece is standing in the way
        Piece enemyPawn = new Pawn(new Point(0, 5), Team.Black, board);
        enemyPawn.placeOnBoard();
        assertEquals(1, pawn.getPossibleMoves().size());

        //single and double move don't work anymore, because now a piece is standing in the way
        enemyPawn.setPosition(new Point(0, 6));
        assertEquals(0, pawn.getPossibleMoves().size());

        //after removing the piece it should be 2 again
        enemyPawn.removeFromBoard();
        assertEquals(2, pawn.getPossibleMoves().size());

        //only single move works after the first move
        Move executeMove = pawn.getPossibleMoves().get(0);
        board.executeMove(executeMove);
        assertEquals(1, pawn.getPossibleMoves().size());

        //udo moe and it should work again
        board.undoMove(executeMove);
        assertEquals(2, pawn.getPossibleMoves().size());
    }

    public void testCapture() {
        //setup
        Board board = new Board();
        Piece pawn = new Pawn(new Point(1, 7), Team.White, board);

        //left side
        Piece enemyPawn1 = new Pawn(new Point(0, 6), Team.Black, board);
        enemyPawn1.placeOnBoard();
        assertEquals(3, pawn.getPossibleMoves().size());

        //right side
        Piece enemyPawn2 = new Pawn(new Point(2, 6), Team.Black, board);
        enemyPawn2.placeOnBoard();
        assertEquals(4, pawn.getPossibleMoves().size());
    }

    public void testEnPassant() {
        //setup
        Board board = new Board();
        Piece pawn = new Pawn(new Point(1, 3), Team.White, board);
        Piece enemyPawn1 = new Pawn(new Point(0, 1), Team.Black, board);
        Piece enemyPawn2 = new Pawn(new Point(2, 1), Team.Black, board);
        Piece enemyPawn3 = new Pawn(new Point(3, 1), Team.Black, board);

        //test before (no en passant is possible)
        assertEquals(2, pawn.getPossibleMoves().size());

        //left side
        board.switchTeam();
        board.executeMove(enemyPawn1.getPossibleMoves().get(1)); //double move
        assertEquals(3, pawn.getPossibleMoves().size());

        //right side
        board.switchTeam();
        board.executeMove(enemyPawn2.getPossibleMoves().get(1)); //double move
        assertEquals(3, pawn.getPossibleMoves().size());
    }
}
