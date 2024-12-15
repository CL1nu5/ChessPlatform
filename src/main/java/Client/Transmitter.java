package Client;

import Support.StringEditor;
import socketio.Socket;

import java.io.*;
import java.util.ArrayList;

public class Transmitter {

    //socket
    final Socket clientSocket;

    //control characters
    public static final int SOH = 0x01;
    public static final int STX = 0x02;
    public static final int ETX = 0x03;
    public static final int ENQ = 0x05;
    public static final int ACK = 0x06;
    public static final int NAK = 0x15;

    public Transmitter(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    /* connection methods */
    public boolean connect(){
        return clientSocket.connect();
    }

    private boolean stopConnection(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /* transmitting methods */
    //sends a message via protocol: doc/Server-Client-Protocol.md
    public boolean transmitMessage(String message, int depth){
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

    public ArrayList<String> receiveMessage(){
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
        try {
            clientSocket.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCharacter(int c){
        try {
            clientSocket.write(((char) c) + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* read methods */
    public int readCharacter(){
        return readLine().charAt(0);
    }
    private ArrayList<String> readMessage(int lineCount){
        ArrayList<String> messages = new ArrayList<>();

        for (int i = 0; i < lineCount; i++){
            messages.add(readLine());
        }

        return messages;
    }

    private String readLine(){
        try {
            return clientSocket.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}