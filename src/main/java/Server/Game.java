package Server;

import ChessObjects.Board;
import ChessObjects.PieceTypes.Team;
import Support.FileEditor;

import java.io.File;
import java.util.ArrayList;

public class Game extends Thread{

    private Board chessBord;
    private CommunicationThread[] players;
    private Team[] teams;

    public Game(CommunicationThread player1, CommunicationThread player2){
        players = new CommunicationThread[]{player1, player2};
        start();
    }

    @Override
    public void run() {
        initiateGame();
    }

    /* stating game */
    public void initiateGame(){
        //getting bord
        chessBord = new Board();
        chessBord.readPosition(new File("save/startPosition/defaultPosition.json"));
        //select random Player who is white
        int i = (int) (Math.random() * 2);

        if (i == 0)
            teams = new Team[]{Team.White, Team.Black};
        else
            teams = new Team[]{Team.Black, Team.White};

        //transmit bords
        transmitBords();
    }

    /* transmit bord */
    public void transmitBords(){
        for (int i = 0; i < players.length; i++){
            transmitTeam(i);
            transmitBord(i);
        }
    }

    public void transmitTeam(int index){

        String json =
                "[\n" +
                "\t{\n" +
                "\t\t\"colour\": \"" + teams[index].string + "\"\n" +
                "\t}\n" +
                "]\n";

        players[index].transmitter.transmitMessage(json, 0);
    }

    public void transmitBord(int index){
        FileEditor fileEditor = new FileEditor();

        ArrayList<String> bordString = fileEditor.read(new File("save/startPosition/defaultPosition.json"));
        StringBuilder json = new StringBuilder();

        for (String line: bordString){
            json.append(line).append("\n");
        }

        players[index].transmitter.transmitMessage(json.toString(), 0);
    }

}
