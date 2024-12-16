package GUI.ChessPanels;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import GUI.ChessPanel;
import GUI.Frame;

import java.awt.*;

public class LocalChessPanel extends ChessPanel {

    private boolean turns;

    public LocalChessPanel(Frame frame, Dimension displaySize, Board chessBoard, Team direction, boolean turns) {
        super(frame, displaySize, chessBoard, direction);
        this.turns = turns;
    }

    @Override
    protected void pressed(Piece piece) {
        if (piece.team.isInSameTeam(chessBoard.activePlayer)){
            selectedPiece = piece;
            grabbedPiece = piece;
        }
    }

    @Override
    protected void execute(Move move) {
        chessBoard.executeMove(move);

        if (turns){
            direction = direction.getOpposite();
        }
    }
}
