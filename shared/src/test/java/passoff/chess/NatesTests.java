package passoff.chess;

import chess.ChessBoard;
import chess.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NatesTests {
    @Test
    @DisplayName("do it work")
    public void natesTest(){
        ChessBoard board = new ChessBoard();
        System.out.println(board.getPiece(new ChessPosition(1,1)).getPieceType());
        System.out.println(board.getPiece(new ChessPosition(1,1)).getTeamColor());
    }
}
