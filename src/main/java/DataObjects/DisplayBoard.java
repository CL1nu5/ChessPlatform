package DataObjects;

import ChessObjects.PieceTypes.Team;

import java.awt.*;
import java.util.ArrayList;

public class DisplayBoard {
    //values
    private final int startX;
    private final int startY;
    public final int fieldLength;
    public final int fieldCount;

    //it takes the full length of the smaller side
    public DisplayBoard(Dimension displaySize, int fieldCount){
        this.fieldCount = fieldCount;

        int min = Math.min(displaySize.width, displaySize.height);
        startX = (displaySize.width - min) / 2;
        startY = (displaySize.height - min) / 2;

        fieldLength = min / this.fieldCount;
    }

    /* getter - get all positions of a Type */
    public ArrayList<PointComparator> getSquarePositions(Team direction){
        ArrayList<PointComparator> positions = new ArrayList<>(getRimPositions());
        positions.addAll(getFieldPositions(direction));

        return positions;
    }

    public ArrayList<PointComparator> getRimPositions(){
        ArrayList<PointComparator> positions = new ArrayList<>();

        //x rims
        for (int y = 0; y < fieldCount; y++){
            for (int x = 0; x < fieldCount; x += (fieldCount - 1)){
                PointComparator compare = new PointComparator(new Point(x, y),
                        getRealPositionOfSquare(new Point(x, y)));
            }
        }

        //y rims:  0 and fieldLength - 1 are excluded, because thy are already cover in x rims
        for (int y = 0; y < fieldCount; y += (fieldCount - 1)){
            for (int x = 1; x < fieldCount - 1; x ++){
                PointComparator compare = new PointComparator(new Point(x, y),
                        getRealPositionOfSquare(new Point(x, y)));
                positions.add(compare);
            }
        }

        return positions;
    }

    public ArrayList<PointComparator> getFieldPositions(Team direction){
        ArrayList<PointComparator> positions = new ArrayList<>();
        int playFieldCount = fieldCount - 2;

        for(int y = 0; y < playFieldCount; y++){
            for (int x = 0; x < playFieldCount; x++){
                PointComparator compare = new PointComparator(new Point(x, y),
                        getRealPositionOfDirectionalFieldSquare(new Point(x, y), direction));
                positions.add(compare);
            }
        }

        return positions;
    }

    /* conditions - they all take the square position */
    private boolean isInBoard(Point pos){
        if (pos.x < 0 || pos.x >= fieldCount){
            return false;
        }

        return pos.y >= 0 && pos.y < fieldCount;
    }

    private boolean isInRim(Point pos){
        if (!isInBoard(pos)){
            return false;
        }

        if (pos.x == 0 || pos.x == fieldCount - 1){
            return true;
        }

        return pos.y == 0 || pos.y == fieldCount - 1;
    }

    private boolean isInField(Point pos){
        return isInBoard(pos) && !isInRim(pos);
    }

    /* conversion getter - they convert relative and real */
    //takes real position
    private Point getRelativePosition(Point pos){
        Point startPos = getStartPoint();
        return new Point(pos.x - startPos.x, pos.y - startPos.y);
    }

    //takes relative position
    private Point getRealPosition(Point pos){
        Point startPos = getStartPoint();
        return new Point(pos.x + startPos.x, pos.y + startPos.y);
    }

    /* conversion getter - they all take real positions */
    //gets square position by real position
    public Point getSquare(Point pos){
        Point relativPos = getRelativePosition(pos);
        return new Point(relativPos.x / fieldLength, relativPos.y / fieldLength);
    }

    //gets playing field position by real position
    private Point getFieldSquare(Point pos){
        Point squarePos = getSquare(pos);

        if (!isInField(squarePos)){
            return null;
        }

         return new Point(squarePos.x - 1, squarePos.y - 1);
    }

    //gets directional playing field by real position
    public Point getDirectionalFieldSquare(Point pos, Team direction){
        Point fieldSquarePos = getFieldSquare(pos);

        if (fieldSquarePos == null){
            return null;
        }

        if(direction.isInSameTeam(Team.White)){
            return fieldSquarePos;
        }

        return new Point(7 - fieldSquarePos.x, 7 - fieldSquarePos.y);
    }

    /* conversion getter - they all take square / field positions */
    //takes field position and turn it into real position
    public Point getRealPositionOfSquare(Point pos){
        Point squarePos = new Point(pos.x * fieldLength, pos.y * fieldLength);
        return getRealPosition(squarePos);
    }

    //takes field position and turn it into real position
    private Point getRealPositionOfFieldSquare(Point pos){
        Point squarePos = new Point(pos.x + 1, pos.y + 1);

        if (!isInField(squarePos)){
            return null;
        }

        return getRealPositionOfSquare(squarePos);
    }

    //takes directional field position and turn it into real position
    private Point getRealPositionOfDirectionalFieldSquare(Point pos, Team direction){
        if (pos == null){
            return null;
        }

        if (direction.isInSameTeam(Team.Black)){
            pos = new Point(7 - pos.x, 7 - pos.y);
        }

        return getRealPositionOfFieldSquare(pos);
    }

    /* playing field basic getter */
    private Point getFieldStartPoint(){
        return new Point(startX + fieldLength, startY + fieldLength);
    }

    private Dimension getFieldSize(){
        int length = fieldLength * (fieldCount - 2);
        return new Dimension(length, length);
    }

    public Rectangle getFieldBounds(){
        return new Rectangle(getFieldStartPoint(), getFieldSize());
    }

    /* basic getter */

    private Point getStartPoint(){
        return new Point(startX, startY);
    }

    private Dimension getSize(){
        int length = fieldLength * fieldCount;
        return new Dimension(length, length);
    }

    public Rectangle getBounds(){
        return new Rectangle(getStartPoint(), getSize());
    }
}
