package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class PawnTest extends TestCase {
    public PawnTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(PawnTest.class);
    }

    public void testGetPossibleMoves(){
        //setup
        Board board = new Board();
        Piece pawn1 = new Pawn(new Point(0,7), Team.White, board);
        pawn1.placeOnBoard();

        /*test single and double move */
        //starting position
        assertEquals(2, pawn1.getPossibleMoves().size());

        Piece enemyPawn1 = new Pawn(new Point(0,5), Team.Black, board);
        enemyPawn1.placeOnBoard();

        assertEquals(1, pawn1.getPossibleMoves().size());

        Piece enemyPawn2 = new Pawn(new Point(0,6), Team.Black, board);
        enemyPawn2.placeOnBoard();

        assertEquals(0, pawn1.getPossibleMoves().size());
        


    }
}
