package DataObjects;

import ChessObjects.PieceTypes.Team;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;
import java.util.ArrayList;

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

    //checks the rim char of different positions
    public void testGetRimChar(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);

        // has no rim char, because it is a corner - team white and black
        Point corner = new Point(0, 0);
        assertEquals("", sizes.getRimChar(corner, Team.White));
        assertEquals("", sizes.getRimChar(corner, Team.Black));

        //has no rim char, because it isn't on the rim - team white and black
        Point noRim = new Point(1, 1);
        assertEquals("", sizes.getRimChar(noRim, Team.White));
        assertEquals("", sizes.getRimChar(noRim, Team.Black));

        //y rim - team white and black
        Point yRim = new Point(1, 0);
        assertEquals("a", sizes.getRimChar(yRim, Team.White));
        assertEquals("h", sizes.getRimChar(yRim, Team.Black));

        //x rim - team white and black
        Point xRim = new Point(0, 3);
        assertEquals("6", sizes.getRimChar(xRim, Team.White));
        assertEquals("3", sizes.getRimChar(xRim, Team.Black));
    }

    //compares the scaled bounds with the previous bounds
    public void testGetScaledBounds(){
        //setup
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        Point position = new Point(500, 600);

        //same size
        Rectangle sameBounds = sizes.getScaledBounds(position, 1);
        assertEquals(position.x, sameBounds.x);
        assertEquals(position.y, sameBounds.y);
        assertEquals(sizes.fieldLength, sameBounds.width);
        assertEquals(sizes.fieldLength, sameBounds.height);

        //smaller
        Rectangle smallerBounds = sizes.getScaledBounds(position, 0.5);
        assertEquals(position.x + sizes.fieldLength / 4, smallerBounds.x);
        assertEquals(position.y + sizes.fieldLength / 4, smallerBounds.y);
        assertEquals(sizes.fieldLength / 2, smallerBounds.width);
        assertEquals(sizes.fieldLength / 2, smallerBounds.height);

        //bigger
        Rectangle biggerBounds = sizes.getScaledBounds(position, 2);
        assertEquals(position.x - sizes.fieldLength / 2, biggerBounds.x);
        assertEquals(position.y - sizes.fieldLength / 2, biggerBounds.y);
        assertEquals(sizes.fieldLength * 2, biggerBounds.width);
        assertEquals(sizes.fieldLength * 2, biggerBounds.height);
    }

    public void testGetRealPositionOfDirectionalFieldSquare(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);

        //not in display board
        assertNull(sizes.getRealPositionOfDirectionalFieldSquare(new Point(-1,0), Team.White));
        assertNull(sizes.getRealPositionOfDirectionalFieldSquare(new Point(-1,0), Team.Black));
        assertNull(sizes.getRealPositionOfDirectionalFieldSquare(new Point(-1,7), Team.Black));
        assertNull(sizes.getRealPositionOfDirectionalFieldSquare(new Point(0,8), Team.Black));


        //field [0,0] - team black and white
        Point realPosition = sizes.getRealPositionOfDirectionalFieldSquare(new Point(0,0), Team.White);
        assertEquals(new Point(180, 80), realPosition);
        realPosition = sizes.getRealPositionOfDirectionalFieldSquare(new Point(0,0), Team.Black);
        assertEquals(new Point(740, 640), realPosition);

        //field [7,7] - team black and white
        realPosition = sizes.getRealPositionOfDirectionalFieldSquare(new Point(7,7), Team.White);
        assertEquals(new Point(740, 640), realPosition);
        realPosition = sizes.getRealPositionOfDirectionalFieldSquare(new Point(7,7), Team.Black);
        assertEquals(new Point(180, 80), realPosition);
    }

    public void testGetSquarePositions(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        ArrayList<PointComparator> squares = sizes.getSquarePositions(Team.White);

        //size must equal right amount
        assertEquals(100, squares.size());

        //sample
        assertEquals(new PointComparator(new Point(0, 0), new Point(100, 0)), squares.get(0));
    }

    public void testGetRimPositions(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        ArrayList<PointComparator> rimSquares = sizes.getRimPositions();

        //size must equal right amount
        assertEquals(36, rimSquares.size());

        //sample
        assertEquals(new PointComparator(new Point(0, 0), new Point(100, 0)), rimSquares.get(0));
    }

    public void testGetFieldPositions(){
        DisplayBoard sizes = new DisplayBoard(new Dimension(1000, 800), 10);
        ArrayList<PointComparator> fieldSquares = sizes.getFieldPositions(Team.White);

        //size must equal right amount
        assertEquals(64, fieldSquares.size());

        //sample
        assertEquals(new PointComparator(new Point(0, 0), new Point(180, 80)), fieldSquares.get(0));
    }
}
