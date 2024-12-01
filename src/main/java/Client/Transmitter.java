package Client;

import Support.StringEditor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Transmitter {

    //socket
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //control characters
    public static final int SOH = 0x01;
    public static final int STX = 0x02;
    public static final int ETX = 0x03;
    public static final int ENQ = 0x05;
    public static final int ACK = 0x06;
    public static final int NAK = 0x15;

    public Transmitter(Socket clientSocket){
        if(!startConnection(clientSocket)){
            throw new RuntimeException("No connection possible");
        }
    }

    /* connection methods */
    private boolean startConnection(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            return clientSocket.isConnected();
        } catch (IOException e) {
            return false;
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

    /* transmitting methods */
    //sends a message via protocol: doc/Server-Client-Protocol.md
    private boolean transmitMessage(String message, int depth){
        if (depth == 4){
            return false;
        }

        //handshake
        sendCharacter(ENQ);

        if (readCharacter() != ACK)
            return false;

        //transmit
        sendCharacter(SOH);
        sendMessage(StringEditor.getLineCounter(message) + "\n");

        sendCharacter(STX);
        sendMessage(message);
        sendCharacter(ETX);

        int response = readCharacter();
        if (response == ACK)
            return true;
        return transmitMessage(message, ++depth);
    }

    private ArrayList<String> receiveMessage(){
        //handshake
        if (readCharacter() != ENQ)
            return receiveMessage();
        sendCharacter(ACK);

        //receive
        if (readCharacter() != SOH)
            return signalWrongReceive();

        int lineCount = Integer.parseInt(readLine());

        if (readCharacter() != STX)
            return signalWrongReceive();

        ArrayList<String> message = readMessage(lineCount);

        if (readCharacter() != ETX)
            return signalWrongReceive();

        sendCharacter(ACK);
        return message;
    }

    private ArrayList<String> signalWrongReceive(){
        sendCharacter(NAK);
        return receiveMessage();
    }

    //receives a message via protocol: doc/Server-Client-Protocol.md

    /* sending methods */
    private void sendMessage(String message){
        out.write(message);
    }

    private void sendCharacter(int c){
        out.write(c);
    }

    /* read methods */
    private int readCharacter(){
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<String> readMessage(int lineCount){
        ArrayList<String> messages = new ArrayList<>();

        for (int i = 0; i < lineCount; i++){
            messages.add(readLine() + "\n");
        }

        return messages;
    }

    private String readLine(){
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
