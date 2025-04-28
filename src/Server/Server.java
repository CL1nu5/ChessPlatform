package Server;

import Client.Transmitter;

import java.io.IOException;
import Socketio.*;
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
        while (!serverSocket.serverSocket.isClosed()){
            Socket client = accept();

            if (client != null){
                Transmitter transmitter = new Transmitter(client);
                CommunicationThread thread = new CommunicationThread(this, transmitter);

                threads.add(thread);
            }

            //stat game if two players are in queue
            if (threads.size() == 2){
                new Game(threads.get(0), threads.get(1));
                break;
            }
        }
    }

    /* client handling */
    public Socket accept(){
        try {
            Socket client = serverSocket.accept();
            logger.info("Client accepted: " + client.socket.getLocalAddress());

            return client;
        } catch (IOException e) {
            return null;
        }
    }
}