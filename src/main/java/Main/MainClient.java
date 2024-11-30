package Main;

import ChessObjects.Board;
import ChessObjects.PieceTypes.Team;
import GUI.ChessPanel;
import GUI.Frame;

import java.awt.*;
import java.io.File;

public class MainClient {
    public static void main(String[] args) {
        Board board = new Board();
        board.readPosition(new File("save/startPosition/defaultPosition.json"));

        Frame frame = new Frame("Chess Game");
        frame.setIcon("res/Icons/frame_icon.png");

        new ChessPanel(frame, new Dimension(1000, 800), board, Team.White);
    }
}
