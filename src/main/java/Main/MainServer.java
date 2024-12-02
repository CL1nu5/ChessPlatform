package Main;

import Server.Server;

public class MainServer {
    public static void main(String[] args) {
        Server server = new Server(4891);
        server.start();
    }
}
