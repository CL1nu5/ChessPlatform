package Main;

import GUI.Frame;
import GUI.MenuPanel;

import java.awt.*;

public class MainClient {
    public static void main(String[] args) {
//        Board board = new Board();
//        board.readPosition(new File("save/startPosition/defaultPosition.json"));
//
//        Client client = new Client(board, "localhost", 4891);
//
//        new ChessPanel(frame, client, new Dimension(1000, 800), board, Team.White);

        Frame frame = new Frame("Chess Game");
        frame.setIcon("res/Icons/frame_icon.png");

        new MenuPanel(frame);

    }
}
