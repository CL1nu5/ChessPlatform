package ChessObjects.PieceTypes;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

public enum Direction {
    /* linear directions */
    Up(-1, 0), Down(1, 0), Right(0, 1), Left(0, -1),
    Up_Right(-1, 1), Up_Left(-1, -1), Down_Right(1, 1), Down_Left(1, -1),
    /* knight directions */
    Up_Up_Right(-2, 1), Up_Up_Left(-2, -1), Down_Down_Right(2, 1), Down_Down_Left(2, -1),
    Right_Right_Up(-1, 2), Right_Right_Down(1, 2), Left_Left_Up(-1, -2), Left_Left_Down(1, -2);

    public final int x, y;

    Direction(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Point getAsPoint() {
        return new Point(x, y);
    }

    public Direction getOpposite(){
        int oppositeX = x * -1;
        int oppositeY = y * -1;

        for (Direction dir: Direction.values()){
            if (dir.x == oppositeX && dir.y == oppositeY){
                return dir;
            }
        }
        return null;
    }

    public static Direction getDirectionByPoint(Point point){
        for (Direction dir: Direction.values()){
            if (dir.x == point.x && dir.y == point.y){
                return dir;
            }
        }
        return null;
    }

    public static Direction getDirectionByDistance(Point from, Point to){
        Point distance = new Point(to.x - from.x, to.y - from.y);

        if (distance.x == 0 && distance.y == 0){
            return null;
        }

        int smaller_number = Math.min(Math.abs(distance.x), Math.abs(distance.y));

        if (smaller_number == 0){
            int bigger_number = Math.max(Math.abs(distance.x), Math.abs(distance.y));
            return getDirectionByPoint(new Point(distance.x / bigger_number, distance.y / bigger_number));
        }

        return getDirectionByPoint(new Point(distance.x / smaller_number, distance.y / smaller_number));
    }

    //piece type Moves
    public static final Direction[] knightMoves =
            {Direction.Up_Up_Right, Direction.Up_Up_Left, Direction.Down_Down_Right, Direction.Down_Down_Left,
            Direction.Right_Right_Up, Direction.Right_Right_Down, Direction.Left_Left_Up, Direction.Left_Left_Down};

    public static final Direction[] rookMoves =
            {Direction.Up, Direction.Down, Direction.Left, Direction.Right};

    public static final Direction[] bishopMoves =
            {Direction.Up_Right, Direction.Up_Left, Direction.Down_Right, Direction.Down_Left};

    public static final Direction[] queenMoves =
            Stream.concat(Arrays.stream(rookMoves), Arrays.stream(bishopMoves)).toArray(Direction[]::new);
}
