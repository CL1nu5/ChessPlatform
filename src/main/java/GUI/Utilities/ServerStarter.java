package GUI.Utilities;

import Server.Server;

public class ServerStarter extends Thread{
    private final int port;

    public ServerStarter(int port){
        this.port = port;
    }

    @Override
    public void run() {
        Server server = new Server(port);
        server.start();
    }
}
