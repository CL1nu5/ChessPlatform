package ChessObjects.PieceTypes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

public class TestDirection extends TestCase {
    public TestDirection(String testName){
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestDirection.class);
    }

    public void testOpposite(){
        assertEquals(Direction.Up, Direction.Down.getOpposite());
        assertEquals(Direction.Left, Direction.Right.getOpposite());
        assertEquals(Direction.Up_Right, Direction.Down_Left.getOpposite());
        assertEquals(Direction.Up_Up_Right, Direction.Down_Down_Left.getOpposite());
    }

    public void testGetDirectionByPoint(){
        assertEquals(Direction.Up, Direction.getDirectionByPoint(new Point(0, -1)));
        assertEquals(Direction.Right, Direction.getDirectionByPoint(new Point(1, 0)));
        assertEquals(Direction.Down_Left, Direction.getDirectionByPoint(new Point(-1, 1)));
        assertEquals(Direction.Up_Up_Left, Direction.getDirectionByPoint(new Point(-1, -2)));
    }

    public void tetGetDirectionByDistance(){
        assertEquals(Direction.Right, Direction.getDirectionByDistance(new Point(0, 0), new Point(1,0)));
        assertEquals(Direction.Right, Direction.getDirectionByDistance(new Point(0, 0), new Point(2,0)));
        assertEquals(Direction.Up_Right, Direction.getDirectionByDistance(new Point(0, 2), new Point(2,0)));
        assertNull(Direction.getDirectionByDistance(new Point(0, 0), new Point(7, 1)));
    }
}
