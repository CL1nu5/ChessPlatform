package DataObjects;

import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class DisplayBoardTest extends TestCase {
    public DisplayBoardTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(DisplayBoardTest.class);
    }

    //tests if the correct length is applied to the field
    public void testConstructor(){
        // 10 = 8 * field + 2 * rim
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        Rectangle bounds = sizes.getBounds();

        //tests if both sides are the same length
        assertEquals(bounds.width, bounds.height);

        //tests if both sides are the length of the smaller side
        assertEquals(800, bounds.width);

        //tests if the starting position is correct
        assertEquals(100, bounds.x);
        assertEquals(0, bounds.y);

        //tests if square length adds up
        assertEquals(bounds.height, sizes.fieldCount * sizes.fieldLength);
    }

    public void testGetFieldBounds(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        Rectangle bounds = sizes.getFieldBounds();

        //tests if both sides are the same length
        assertEquals(bounds.width, bounds.height);

        //tests if both sides are 8/10 of the display field side
        assertEquals(640, bounds.width);

        //tests if the stating pos is correct
        assertEquals(180, bounds.x);
        assertEquals(80, bounds.y);
    }

    //paints how the board looks for team white
    public void testWritePositions(){
        System.out.println("----------------------------Board----------------------------");

        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        for (int y = 0; y < sizes.fieldCount * sizes.fieldLength; y += 40){
            for (int x = 100; x < sizes.fieldCount * sizes.fieldLength + 100; x += 40){
                Point pos = sizes.getDirectionalFieldSquare(new Point(x, y), Team.White);
                System.out.print(" ");

                if (pos == null) {
                    System.out.print("nl");
                    continue;
                }
                System.out.print(pos.x + "" + pos.y);

            }
            System.out.print("\n");
        }
    }

    public void testGetDirectionalFieldSquare(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);

        //test if it returns null if position is outside field
        Point pos = sizes.getDirectionalFieldSquare(new Point(0, 0), Team.White);
        assertNull(pos);

        //test square [0,0]
        pos = sizes.getDirectionalFieldSquare(new Point(180, 80), Team.White);
        assertEquals(new Point(0, 0), pos);

        pos = sizes.getDirectionalFieldSquare(new Point(180, 80), Team.Black);
        assertEquals(new Point(7, 7), pos);

        //test square [3,6]
        pos = sizes.getDirectionalFieldSquare(new Point(420, 560), Team.White);
        assertEquals(new Point(3, 6), pos);

        pos = sizes.getDirectionalFieldSquare(new Point(420, 560), Team.Black);
        assertEquals(new Point(4, 1), pos);
    }
}
