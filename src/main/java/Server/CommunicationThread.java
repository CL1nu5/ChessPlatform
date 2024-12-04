package Server;

import Client.Transmitter;

public class CommunicationThread extends Thread {
    private Server server;
    private Transmitter transmitter;

    CommunicationThread(Server server, Transmitter transmitter){
        this.server = server;
        this.transmitter = transmitter;

        start();
    }

    public void run(){

    }

}
