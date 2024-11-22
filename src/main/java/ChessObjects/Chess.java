package ChessObjects;

import java.awt.*;

public class Chess {

    //translates (x,y) to (c,n)
    public static String translatePosToCoords(Point position){
        try {
            String x = String.valueOf((char) (position.x + 97));
            String y = String.valueOf(8 - position.y);
            return x + y;
        }
        catch (Exception e){
            return null;
        }
    }

    //translates (c,n) to (x,y)
    public static Point translateCoordsToPos(String coordinates){
        try {
            String[] vals = coordinates.split("");
            int x = (int) (vals[0].charAt(0)) - 97;
            int y = 8 - Integer.parseInt(vals[1]);
            return new Point(x, y);
        }
        catch (Exception e){
            return null;
        }
    }
}
