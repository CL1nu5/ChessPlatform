package DataObjects;

import java.awt.*;

public class PointComparator {
    public Point from, to;

    PointComparator(Point from, Point to){
        this.from = from;
        this.to = to;
    }

    public String toString(){
        return "[from:{" + from.x + ";" + from.y + "}" + "," + "to:{" + to.x + ";" + to.y + "}"+ "]";
    }
}
