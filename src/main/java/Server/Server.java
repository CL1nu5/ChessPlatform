package Server;

import Client.Transmitter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server {

    ServerSocket serverSocket;
    ArrayList<CommunicationThread> threads;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Server(int port){
        threads = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* control methods */
    //server listens for requests
    public void start(){
        while (! serverSocket.isClosed()){
            Socket client = accept();

            if (client != null){
                Transmitter transmitter = new Transmitter(client);
                CommunicationThread thread = new CommunicationThread(this, transmitter);

                threads.add(thread);
            }
        }
    }

    /* client handling */
    public Socket accept(){
        try {
            Socket client = serverSocket.accept();
            logger.info("Client accepted: " + client.getRemoteSocketAddress());

            return client;
        } catch (IOException e) {
            return null;
        }
    }


}
