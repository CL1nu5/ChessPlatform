package ChessObjects.PieceTypes;

import java.awt.*;

public enum Direction {
    /* linear directions */
    Up(-1, 0), Down(1, 0), Right(0, 1), Left(0, -1),
    Up_Right(-1, 1), Up_Left(-1, -1), Down_Right(1, 1), Down_Left(1, -1),
    /* knight directions */
    Up_Up_Right(-2, 1),;

    public final int x, y;

    Direction(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Point getAsPoint() {
        return new Point(x, y);
    }
}
