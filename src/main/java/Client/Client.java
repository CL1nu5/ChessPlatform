package Client;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import GUI.ChessPanel;
import GUI.Frame;
import Support.StringEditor;

import java.awt.*;
import java.io.*;
import socketio.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client{

    private ChessPanel panel;
    private Transmitter transmitter;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //constructor stating connection
    public Client(String ip, int port, Frame frame, Dimension displaySize){
        //connection
        transmitter = connect(ip, port);
        transmitter.connect();

        //getting bord and team
        Team team = getTeam();
        System.out.println("got team");
        Board chessBord = getBord();

        //starting panel
        panel = new ChessPanel(frame, this, displaySize, chessBord, team);
    }

    /* interaction Methods */
    public Team getTeam(){
        String string = StringEditor.turnJsonListIntoString(transmitter.receiveMessage());
        System.out.println("got massage");
        return Team.getTeamViaJson(string);
    }

    public Board getBord(){
        Board board = new Board();
        board.setPositionByString(transmitter.receiveMessage());

        return board;
    }

    public ArrayList<Move> getPossibleMoves(Piece piece){
        return piece.getMoves(); // todo
    }

    public boolean executeMove(Move move){
        panel.chessBoard.executeMove(move);
        return true; // todo
    }

    /* socket methods */
    public Transmitter connect(String ip, int port){
        try {
            Socket clientSocket = new Socket(ip, port);
            return new Transmitter(clientSocket);
        } catch (IOException e) {
            logger.warning("Connection to: " + ip + ", not possible");
        }
        return null;
    }
}
