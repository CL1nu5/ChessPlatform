package Client;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

    private Board board;
    private Transmitter transmitter;

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
            throw new RuntimeException(e);
        }
    }
}
