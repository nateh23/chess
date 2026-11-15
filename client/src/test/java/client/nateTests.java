package client;

import chess.ChessBoard;
import chess.ChessGame;
import org.junit.jupiter.api.Test;

public class nateTests {
    @Test
    public void yeah(){
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        BoardRenderer uh = new BoardRenderer();

        uh.RenderBoard(board, ChessGame.TeamColor.BLACK);
    }
}
