package Client;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client{

    private Board board;
    private Transmitter transmitter;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //constructor stating connection
    public Client(Board board, String ip, int port){
        this.board = board;

        //connection
        transmitter = connect(ip, port);
    }

    /* interaction Methods */
    public ArrayList<Move> getPossibleMoves(Piece piece){
        return piece.getMoves(); // todo
    }

    public boolean executeMove(Move move){
        board.executeMove(move);
        return true; // todo
    }

    /* socket methods */
    public Transmitter connect(String ip, int port){
        try {
            Socket clientSocket = new Socket(ip, port);
            return new Transmitter(clientSocket);
        } catch (IOException e) {
            logger.warning("Connection to:" + ip + ", not possible");
        }
        return null;
    }
}
