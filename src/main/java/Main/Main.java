package Main;

import ChessObjects.Board;
import GUI.ChessPanel;
import GUI.Frame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame("Chess Game");
        frame.setIcon("res/Icons/frame_icon.png");

        Board board = new Board();

        new ChessPanel(frame, board, new Dimension(1000, 800));

    }
}
