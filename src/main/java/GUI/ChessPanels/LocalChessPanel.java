package GUI.ChessPanels;

import ChessObjects.Board;
import ChessObjects.Move;
import ChessObjects.Piece;
import ChessObjects.PieceTypes.Team;
import DataObjects.DisplayBoard;
import GUI.ChessPanel;
import GUI.Frame;
import Support.StringEditor;

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

        winner = isGameOver();

        if (turns){
            direction = direction.getOpposite();
        }
    }

    @Override
    protected void paintGameOver(Graphics2D g) {
        DisplayBoard sizes = new DisplayBoard(displaySize, 10); // 10 = 8 * field + 2 * rim

        Point start = sizes.getRealPositionOfSquare(new Point(0, 4));
        Point end = sizes.getRealPositionOfSquare(new Point(10, 6));

        Dimension size = new Dimension(displaySize.width, end.y - start.y);

        g.setColor(ChessPanel.MOVE_COLOR);
        g.fillRect(0, start.y, size.width, size.height);

        g.setColor(ChessPanel.LIGHT_COLOR);
        g.setFont(new Font("Serif", Font.PLAIN, 50));
        String text = "Game over! Team: " + winner.string + " has won";
        Dimension stringSize = StringEditor.getStringSize(text, g.getFont());

        g.drawString(text, (size.width - stringSize.width) / 2, start.y + size.height - (size.height - stringSize.height) / 2);
    }
}
