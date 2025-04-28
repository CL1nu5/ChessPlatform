package ChessObjects;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import ChessObjects.Pieces.King;
import ChessObjects.Pieces.Pawn;
import ChessObjects.Pieces.Rook;
import Support.FileEditor;
import Support.StringEditor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

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
        assertEquals(new Point(1, 1), move1.getDistance());
        assertEquals(new Point(4, 2), move2.getDistance());
        assertEquals(new Point(0, 0), move3.getDistance());
        assertEquals(new Point(-1, 6), move4.getDistance());
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

    public void testGetMoveFromJson(){
        //setup
        Board board = new Board();
        FileEditor fileEditor = new FileEditor();
        ArrayList<String> content = fileEditor.read(new File("save/test/testMove.json"));

        String json = StringEditor.turnJsonListIntoString(content);

        Piece pawn = new Pawn(new Point(0,0), Team.White, board);
        pawn.placeOnBoard();

        Piece enemy = new Pawn(new Point(2,2), Team.Black, board);
        enemy.placeOnBoard();

        Piece rook = new Rook(new Point(1,4), Team.White, board);
        rook.placeOnBoard();

        //getting move
        Move move = new Move(json, board);
        System.out.println(move);
    }

    public void testGetJsonFromMove(){
        //setup
        Board board = new Board();

        Pawn pawn = new Pawn(new Point(1, 1), Team.White, board);
        pawn.placeOnBoard();

        Rook rook = new Rook(new Point(0, 7), Team.White, board);
        King king = new King(new Point(3, 7), Team.White, board);
        rook.placeOnBoard();
        king.placeOnBoard();

        //test
        System.out.println(pawn.getMoves().getFirst().getAsJson());
        System.out.println(king.getMoves().getLast().getAsJson());
    }
}
