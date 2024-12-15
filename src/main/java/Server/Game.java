package Server;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.PieceTypes.Team;
import Client.Transmitter;
import Support.FileEditor;
import Support.StringEditor;

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

        //start game loop
        loop();
    }

    /* game loop */
    private void loop(){
        int activePlayer = getIndexOfCurrentPlayer();

        Move move = getNextMove(activePlayer);

        communicateMove(1 - activePlayer, move); // comunicate the move
        chessBord.executeMove(move);

        //check if someone has won
        if (chessBord.getMoves().isEmpty()){
            System.out.println(teams[activePlayer].string + " has won");
            return;
        }

        loop();
    }

    //gets the index of the team in charge
    private int getIndexOfCurrentPlayer(){
        if (teams[0].isInSameTeam(chessBord.activePlayer)){
            return 0;
        }
        return 1;
    }

    /* transmit bord */
    private void transmitBords(){
        for (int i = 0; i < players.length; i++){
            transmitTeam(i);
            transmitBord(i);
        }
    }

    private void transmitTeam(int index){

        String json =
                "[\n" +
                "\t{\n" +
                "\t\t\"colour\": \"" + teams[index].string + "\"\n" +
                "\t}\n" +
                "]\n";

        players[index].transmitter.transmitMessage(json, 0);
    }

    private void transmitBord(int index){
        FileEditor fileEditor = new FileEditor();

        ArrayList<String> bordString = fileEditor.read(new File("save/startPosition/defaultPosition.json"));
        StringBuilder json = new StringBuilder();

        for (String line: bordString){
            json.append(line).append("\n");
        }

        players[index].transmitter.transmitMessage(json.toString(), 0);
    }

    /* transmit move */
    private Move getNextMove(int player){
        Transmitter t = players[player].transmitter;

        t.transmitMessage("+expecting-move+\n", 0);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> message = t.receiveMessage();
        String json = StringEditor.turnJsonListIntoString(message);

        System.out.println(json);

        return new Move(json, chessBord);
    }

    private void communicateMove(int player, Move move){
        Transmitter t = players[player].transmitter;

        t.transmitMessage("+enemy-move+\n", 0);
        t.transmitMessage(move.getAsJson(), 0);
    }
}
