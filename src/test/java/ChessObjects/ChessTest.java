package ChessObjects;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;


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
        String coordinate = "a2";

        //test translating twice, to turn it back into original
        String resultCoordinate = Chess.translatePosToCoords(Chess.translateCoordsToPos(coordinate));
        assertEquals(coordinate, resultCoordinate);

        Point resultPosition = Chess.translateCoordsToPos(Chess.translatePosToCoords(position));
        assertEquals(position, resultPosition);
    }
}
