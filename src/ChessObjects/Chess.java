package ChessObjects;

import java.awt.*;

public class Chess {

    //translates (x,y) to (c,n)
    public static String translatePosToCoords(Point position) {
        String x = String.valueOf((char) (position.x + 97));
        String y = String.valueOf(8 - position.y);

        if (position.x < 0 || position.x > 7) {
            x = " ";
        }
        if (position.y < 0 || position.y > 7) {
            y = " ";
        }

        return x + y;
    }

    //translates (c,n) to (x,y)
    public static Point translateCoordsToPos(String coordinates) {
        //get the 2 values
        if (coordinates.length() != 2) {
            return null;
        }
        String[] vals = coordinates.split("");

        try {
            int x = (int) (vals[0].charAt(0)) - 97;
            int y = 8 - Integer.parseInt(vals[1]);

            //x and y need to be between 0 and 7
            if (x > 7 || x < 0 || y > 7 || y < 0) {
                return null;
            }

            return new Point(x, y);
        } catch (Exception e) {
            return null;
        }

    }
}
