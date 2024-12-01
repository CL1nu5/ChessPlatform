package Main;

import ChessObjects.Board;
import ChessObjects.PieceTypes.Team;
import Client.Client;
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

        Client client = new Client(board, "localhost", 4891);

        new ChessPanel(frame, client, new Dimension(1000, 800), board, Team.White);
    }
}
