package GUI.ChessPanels;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import Client.Client;
import GUI.ChessPanel;
import GUI.Frame;

import java.awt.*;

public class RemoteChessPanel extends ChessPanel {

    //communication
    protected final Client client;

    public RemoteChessPanel(Frame frame, Dimension displaySize, Board chessBoard, Team direction, Client client) {
        super(frame, displaySize, chessBoard, direction);
        this.client = client;
    }

    @Override
    protected void pressed(Piece piece) {
        if (piece.team.isInSameTeam(direction)) {
            selectedPiece = piece;
            grabbedPiece = piece;
        }
    }

    protected void execute(Move move) {
        client.executeMove(move);
        checkGameOver();
    }

    @Override
    protected void paintGameOver(Graphics2D g) {
        String text = "Game over! ";

        if (winner != null){
            if (winner.isInSameTeam(direction)){
                text += "You have WON :)";
            }
            else {
                text += "You have LOST :(";
            }
        }
        else {
            text += "remis - draw";
        }

        paintGameOver(g, text);
    }
}