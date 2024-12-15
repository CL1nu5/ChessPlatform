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

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client {

    private final ChessPanel panel;
    private final Transmitter transmitter;

    private final Board chessBord;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private boolean waiting;

    //constructor stating connection
    public Client(String ip, int port, Frame frame, Dimension displaySize) throws ConnectException{
        //connection
        transmitter = connect(ip, port);
        if(!transmitter.connect()){
            throw new ConnectException("Can't connect to server!");
        }

        //getting bord and team
        Team team = getTeam();
        logger.info("Team received");

        chessBord = getBord();
        logger.info("Chess bord received");

        //starting panel
        frame.setResizable(true);
        panel = new ChessPanel(frame, this, displaySize, chessBord, team);

        //getting command
        getCommand();
    }

    /* interaction Methods */
    public Team getTeam(){
        String string = StringEditor.turnJsonListIntoString(transmitter.receiveMessage());
        return Team.getTeamViaJson(string);
    }

    public Board getBord(){
        Board board = new Board();
        board.setPositionByString(transmitter.receiveMessage());

        return board;
    }

    public ArrayList<Move> getMoves(Piece piece){
        return piece.getMoves();
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

    /* constant reading */
    public void getCommand(){
        waiting = true;
        String command = transmitter.receiveMessage().get(0);

        switch (command){
            case "+enemy-move+" -> {
                ArrayList<String> message = transmitter.receiveMessage();
                String json = StringEditor.turnJsonListIntoString(message);
                chessBord.executeMove(new Move(json, chessBord));
                getCommand();
            }

            case "+expecting-move+" -> {
                waiting = false;
            }

            default -> {
                logger.warning("Command: " + command + ", not found!");
            }
        }
    }
}
