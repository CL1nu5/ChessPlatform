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
    public void transmitMessage(String message, int depth){
        sendMessage(StringEditor.getLineCounter(message) + "\n");
        sendMessage(message);
    }

    public ArrayList<String> receiveMessage(){
        int lineCount = Integer.parseInt(readLine());
        return readMessage(lineCount);
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
