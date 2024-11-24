package DataObjects;

import ChessObjects.Board;

import java.awt.*;

public class DisplayBoard {
    public int startX;
    public int startY;
    public int fieldLength;
    public int fieldCount;

    public DisplayBoard(Dimension displaySize, Board board){
        fieldCount = board.pieces.length + 2;

        int min = Math.min(displaySize.width, displaySize.height);
        startX = (displaySize.width - min) / 2;
        startY = (displaySize.height - min) / 2;
        fieldLength = min / fieldCount;
    }

    /* information getter */
    public boolean isInRim(Point position){
        if (!isInDisplay(position)){
            return false;
        }

        return ((position.x < startX + fieldLength || position.x >= getMaxX() - fieldLength) ||
               (position.y < startY + fieldLength || position.y >= getMaxY() - fieldLength));
    }

    public boolean isInBoard(Point position){
        if (!isInDisplay(position)){
            return false;
        }

        return !isInRim(position);
    }

    public boolean isInDisplay(Point position){
        return position.x >= startX && position.x <= getMaxX() && position.y >= startY && position.y <= getMaxY();
    }

    /* position getter */
    public Point getIndexPosition(int x, int y){
        return new Point(startX + fieldLength * x, startY + fieldLength * y);
    }

    public Point getMinPoint(){
        return new Point(startX, startY);
    }

    public Point getMaxPoint(){
        return new Point(getMaxX(), getMaxY());
    }

    public int getMaxX(){
        return startX + fieldLength * fieldCount;
    }

    public int getMaxY(){
        return startY + fieldLength * fieldCount;
    }
}
