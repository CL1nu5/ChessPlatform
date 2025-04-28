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

    public boolean equals(Object that){
        if (that instanceof PointComparator){
            PointComparator other = (PointComparator) that;
            return other.from.equals(this.from) && other.to.equals(this.to);
        }
        return false;
    }
}
