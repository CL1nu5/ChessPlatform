package ChessObjects.Pieces;

import ChessObjects.Board;
import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class KnightTest extends TestCase {
    public KnightTest (String testName){
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(KnightTest.class);
    }

    public void testPossibleMoves(){
        //setup
        Board board = new Board();
        Knight knight = new Knight(new Point(4,4), Team.White, board);
        knight.placeOnBoard();

        //general move test
        assertEquals(8, knight.getPossibleMoves().size());
    }
}
