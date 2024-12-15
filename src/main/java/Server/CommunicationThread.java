package Server;

import Client.Transmitter;

public class CommunicationThread extends Thread {
    private Server server;
    public Transmitter transmitter;

    CommunicationThread(Server server, Transmitter transmitter){
        this.server = server;
        this.transmitter = transmitter;
    }

    public void run(){

    }

}
