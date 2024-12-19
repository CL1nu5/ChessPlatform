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
    }

    @Override
    protected void paintGameOver(Graphics2D g) {
        //todo
    }
}
