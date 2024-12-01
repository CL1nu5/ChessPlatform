package Client;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import Support.StringEditor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

    private Board board;

    //socket
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //control characters
    public static final int SOH = 0x01;
    public static final int STX = 0x01;
    public static final int ENQ = 0x01;
    public static final int ACK = 0x01;
    public static final int NAK = 0x01;

    //constructor stating connection
    public Client(Board board, String ip, int port){
        this.board = board;

        //connection
        startConnection(ip, port);
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
    private boolean startConnection(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            return clientSocket.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

    //sends a massage via protocol: doc/Server-Client-Protocol.md
    private boolean transmitMassage(String massage, int depth){
        if (depth == 4){
            return false;
        }

        //handshake
        sendCharacter(ENQ);

        if (receiveCharacter() != ACK)
            return false;

        //transmit
        sendCharacter(SOH);
        sendCharacter(StringEditor.getLineCounter(massage));

        sendCharacter(STX);
        sendMassage(massage);

        int response = receiveCharacter();
        if (response == ACK)
            return true;
        return transmitMassage(massage, ++depth);
    }

    private void sendMassage(String massage){
        out.write(massage);
    }

    private void sendCharacter(int c){
        out.write(c);
    }

    public int receiveCharacter(){
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean stopConnection(){
        try {
            clientSocket.close();
            in.close();
            out.close();

        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
