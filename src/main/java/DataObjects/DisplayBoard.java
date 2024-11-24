package DataObjects;

import ChessObjects.Board;
import Support.StringEditor;

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

        return (isInXRim(position) || isInYRim(position));
    }

    public boolean isInXRim(Point position){
        return position.x < startX + fieldLength || position.x >= getMaxX() - fieldLength;
    }
    public boolean isInYRim(Point position){
        return position.y < startY + fieldLength || position.y >= getMaxY() - fieldLength;
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

    public Point getStringStartingPos(int indexX, int indexY, String string, Font font){
        Point filedPos = getIndexPosition(indexX, indexY);
        Dimension stringSize = StringEditor.getStringSize(string, font);

        int x = filedPos.x + (fieldLength - stringSize.width) / 2;
        int y = filedPos.y + stringSize.height;

        return new Point(x, y);
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

    /* size getter */
    public Dimension getFieldSize(){
        return new Dimension(fieldLength, fieldLength);
    }

    public Dimension getScaledFiledSize(double scale){
        Dimension fieldSize = getFieldSize();
        return new Dimension((int) (fieldSize.width * scale), (int) (fieldSize.height * scale));
    }
}
