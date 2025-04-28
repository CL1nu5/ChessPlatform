package ChessObjects;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;
import java.util.Objects;


public class ChessTest extends TestCase {
    public ChessTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(ChessTest.class);
    }

    //tests if a position (starting in the top right corner ant (x=0, y=0) can be turned into a chess notation e.g. f3
    public void testTranslatePosToCoords(){
        //setup
        Point position1 = new Point(1,5);
        Point position2 = new Point(7,0);
        Point position3 = new Point(2,7);

        //tests
        assertEquals("b3", Chess.translatePosToCoords(position1));
        assertEquals("h8", Chess.translatePosToCoords(position2));
        assertEquals("c1", Chess.translatePosToCoords(position3));
    }

    //tests if a chess notation e.g. f3 can be turned into a position (starting in the top right corner ant (x=0, y=0)
    public void testTranslateCoordsToPos(){
        //setup
        String coordinate1 = "d5";
        String coordinate2 = "a1";
        String coordinate3 = "f2";

        //test
        assertEquals(new Point(3,3), Chess.translateCoordsToPos(coordinate1));
        assertEquals(new Point(0,7), Chess.translateCoordsToPos(coordinate2));
        assertEquals(new Point(5,6), Chess.translateCoordsToPos(coordinate3));
    }

    //tests if both translations, can reverse each other
    public void testBothTranslations(){
        //setup
        Point position = new Point(1,2);
        String coordinate = "h8";

        //test translating twice, to turn it back into original
        String resultCoordinate = Chess.translatePosToCoords(Chess.translateCoordsToPos(coordinate));
        assertEquals(coordinate, resultCoordinate);

        Point resultPosition = Chess.translateCoordsToPos(Objects.requireNonNull(Chess.translatePosToCoords(position)));
        assertEquals(position, resultPosition);
    }

    /* edge cases */
    //tests the method translateCoordsToPos for wrong length inputs
    public void testNotJustTwoCharacters(){
        //to long
        assertNull(Chess.translateCoordsToPos("a22"));
        assertNull(Chess.translateCoordsToPos("aa2"));

        //to short
        assertNull(Chess.translateCoordsToPos("a"));
        assertNull(Chess.translateCoordsToPos("2"));
    }

    //tests the method translateCoordsToPos for wrong type inputs
    public void testWrongCharacterInputs(){
        //fliped
        assertNull(Chess.translateCoordsToPos("2a"));
        assertNull(Chess.translateCoordsToPos("5f"));

        //only one type
        assertNull(Chess.translateCoordsToPos("aa"));
        assertNull(Chess.translateCoordsToPos("33"));
    }

    //tests the method translateCoordsToPos for wrong inputs
    public void testCoordinateNotInBoard(){
        //x to small and big
        assertNull(Chess.translateCoordsToPos("A3"));
        assertNull(Chess.translateCoordsToPos("j2"));

        //y to small and big
        assertNull(Chess.translateCoordsToPos("a0"));
        assertNull(Chess.translateCoordsToPos("a9"));

        //both to small and big
        assertNull(Chess.translateCoordsToPos("A0"));
        assertNull(Chess.translateCoordsToPos("j9"));
    }

    //tests the method translatePosToCoords for wrong Position inputs
    public void testsPositionNotInBoard(){
        //x to small and big
        assertEquals(" 6", Chess.translatePosToCoords(new Point(-1, 2)));
        assertEquals(" 6", Chess.translatePosToCoords(new Point(8, 2)));

        //y to small and big
        assertEquals("b ", Chess.translatePosToCoords(new Point(1, -1)));
        assertEquals("b ", Chess.translatePosToCoords(new Point(1, 8)));

        //both to small and big
        assertEquals("  ", Chess.translatePosToCoords(new Point(-1, -1)));
        assertEquals("  ", Chess.translatePosToCoords(new Point(8, 8)));
    }
}
