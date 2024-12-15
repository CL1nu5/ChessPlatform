package ChessObjects;

import ChessObjects.Pieces.King;
import Support.FileEditor;
import Support.StringEditor;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Move implements Cloneable{
    public Piece movingPiece, capturedPiece;
    public Point previousPosition, postponedPosition;
    public Move connectedMove;
    public Board board;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //general constructor
    public Move(Piece movingPiece, Piece capturedPiece, Point postponedPosition, Move connectedMove) {
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.previousPosition = movingPiece.currentPosition;
        this.postponedPosition = postponedPosition;
        this.connectedMove = connectedMove;
        this.board = movingPiece.board;
    }

    //constructor for basic moves (non capture)
    public Move(Piece movingPiece, Point postponedPosition) {
        this(movingPiece, null, postponedPosition, null);
    }

    //constructor, for getting a move via json
    public Move(String json, Board board){
        this.board = board;

        setMoveViaJson(json);
    }

    /* capture methods */
    //checks if a piece was captured by this move
    public boolean isCaptureMove() {
        return capturedPiece != null;
    }

    public boolean isKingCaptured() {
        if (!isCaptureMove()) {
            return false;
        }
        return capturedPiece instanceof King;
    }

    /* updating moves */
    public void execute() {
        removeCapturedPiece();

        if (!movingPiece.setPosition(postponedPosition)) {
            logger.warning("move:execute - position update not possible: " + this);
        }

        if (connectedMove != null) {
            connectedMove.execute();
        }
    }

    public void undo() {
        if (connectedMove != null) {
            connectedMove.undo();
        }

        if (!movingPiece.setPosition(previousPosition)) {
            logger.warning("move:undo - position update not possible: " + this);
        }

        addCapturedPiece();
    }

    //execution methods
    public void removeCapturedPiece() {
        if (isCaptureMove()) {
            capturedPiece.removeFromBoard();
        }
    }

    //undo methods
    public void addCapturedPiece() {
        if (isCaptureMove()) {
            Point addPos = capturedPiece.currentPosition;
            board.pieces[addPos.y][addPos.x] = capturedPiece;
        }
    }

    public boolean isIllegalMove(){
        boolean returnValue = false;
        board.executeMove(this);

        ArrayList<Move> counterMoves = board.getPossibleMoves();
        //check if any counter move captures the king -> non-legal move
        for (Move counterMove : counterMoves) {
            if (counterMove.isKingCaptured()) {
                returnValue = true;
                break;
            }
        }

        board.undoMove(this);
        return returnValue;
    }

    /* position methods */
    public Point getDistance() {
        return new Point(postponedPosition.x - previousPosition.x, postponedPosition.y - previousPosition.y);
    }

    /* methods getting move via json */
    //sets values got from json protocol is documented: doc/JSON-formats.md
    public void setMoveViaJson(String json) {
        int index = 0;
        char current;

        while (index < json.length() && (current = json.charAt(index)) != ']') {
            if (current == '{') {
                String moveJson = StringEditor.collectFromTill(++index, '}', json);
                HashMap<String, String> moveValues = StringEditor.getValuesFromJson(moveJson, 4, 2);

                this.movingPiece = getPieceFromHash(moveValues.get("moving-piece"));
                this.capturedPiece = getPieceFromHash(moveValues.get("captured-piece"));

                this.previousPosition = this.movingPiece.currentPosition;
                this.postponedPosition = getPositionFromHash(moveValues.get("postponed-position"));

                this.connectedMove = null;
                if (!moveValues.get("connected-move").isEmpty()){
                    this.connectedMove = new Move(moveJson.substring(1), board);
                }
                return;
            }

            index++;
        }
    }

    private Piece getPieceFromHash(String position){
        Point pos = getPositionFromHash(position);

        if (pos == null){
            return null;
        }

        return board.getPiece(pos);
    }

    private Point getPositionFromHash(String position){
        String [] vals = position.split(";");

        if (vals.length != 2){
            return null;
        }

        int x = Integer.parseInt(vals[0]), y = Integer.parseInt(vals[1]);

        return new Point(x, y);
    }

    /* getting the move as a json */
    public String getAsJson(){
        return "[\n" +
                getMoveJson(1) +
                "]\n";
    }

    private String getMoveJson(int depth){
        StringBuilder s = new StringBuilder();
        String indent = getIndentation(depth);
        String indentContent = getIndentation(depth + 1);

        s.append(indent).append("{\n");

        s.append(indentContent).append("\"moving-piece\": \"").append(getStringLocation(movingPiece.currentPosition)).append("\",\n");

        //captured piece
        s.append(indentContent).append("\"captured-piece\": \"");
        if (capturedPiece == null){
            s.append("\",\n");
        }
        else {
            s.append(getStringLocation(capturedPiece.currentPosition)).append("\",\n");
        }

        s.append(indentContent).append("\"postponed-position\": \"").append(getStringLocation(postponedPosition)).append("\",\n");

        //connected move
        s.append(indentContent).append("\"connected-move\": ");
        if (connectedMove == null){
            s.append("\"\"\n");
        }
        else {
            s.append("\n");
            s.append(connectedMove.getMoveJson(depth + 1));
        }

        //end
        s.append(indent).append("}\n");

        return s.toString();
    }

    private String getIndentation(int depth){
        return "\t".repeat(Math.max(0, depth));
    }

    private String getStringLocation(Point position){
        return position.x + "," + position.y;
    }

    /* fundamental objekt methods */
    public String toString() {
        return "{movingP: " + movingPiece + "; capturedP: " + capturedPiece + "; previousP: " + previousPosition
                + "; postponedP: " + postponedPosition + "; connected Move: " + connectedMove + "}";
    }

    public boolean isSimular(Move that) {
        if (that == null) {
            return false;
        }

        //connected move
        boolean connected = true;
        if (this.connectedMove != null && that.connectedMove != null) {
            connected = connectedMove.isSimular(that.connectedMove);
        } else if (this.connectedMove != null || that.connectedMove != null) {
            return false;
        }

        //current move
        if (board.pieceNotSimilar(this.movingPiece, that.movingPiece)) {
            return false;
        }
        if (board.pieceNotSimilar(this.capturedPiece, that.capturedPiece)) {
            return false;
        }
        if (!this.previousPosition.equals(that.previousPosition)) {
            return false;
        }

        return this.postponedPosition.equals(that.postponedPosition) && connected;

    }

    //clones everything except board
    public Move clone(Board cloneBoard) {
        try {
            Move clone = (Move) super.clone();
            //pieces
            clone.movingPiece = cloneBoard.getPiece(movingPiece.currentPosition);
            if(capturedPiece != null) {
                clone.capturedPiece = cloneBoard.getPiece(capturedPiece.currentPosition);
            }
            else {
                clone.capturedPiece = null;
            }

            //positions
            clone.previousPosition = clone.movingPiece.currentPosition;
            clone.postponedPosition = (Point) postponedPosition.clone();

            //other
            if(connectedMove != null) {
                clone.connectedMove = connectedMove.clone(cloneBoard);
            }
            else {
                clone.connectedMove = null;
            }
            clone.board = cloneBoard;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
