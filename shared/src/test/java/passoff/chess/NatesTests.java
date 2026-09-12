package passoff.chess;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.text.Position;

public class NatesTests {
    @Test
    @DisplayName("do it work")
    public void natesTest(){
        ChessBoard board = new ChessBoard();
        System.out.println(board.getPiece(new ChessPosition(1,1)).getPieceType());
        System.out.println(board.getPiece(new ChessPosition(1,1)).getTeamColor());
    }

    public String convertPieceToString(ChessPiece piece){
        String team;
        String pieceType;

        if (piece == null){
            return "  ";
        }

        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            team = "b";
        }else{
            team = "w";
        }

        pieceType = switch (piece.getPieceType()){
            case ChessPiece.PieceType.BISHOP -> "b";
            case ChessPiece.PieceType.KING -> "k";
            case ChessPiece.PieceType.PAWN -> "p";
            case ChessPiece.PieceType.QUEEN -> "q";
            case ChessPiece.PieceType.KNIGHT -> "n";
            case ChessPiece.PieceType.ROOK -> "r";

            default -> "blank";
        };

        return team + pieceType;
    }

    @Test
    @DisplayName("present board")
    public void presentBoard(){
        ChessBoard board = new ChessBoard();

        for (int i = 0; i < 8; i++){
            System.out.println("___________________________________________________________________________");
            String boardLine = "";
            for (int a = 0; a < 8; a++){
                ChessPiece pieceFound = board.getPiece(new ChessPosition(a + 1,8 - i));
                String pieceValue = convertPieceToString(pieceFound);

                boardLine = boardLine + pieceValue + "   |   ";
            }
            System.out.println(boardLine);
        }

    }
}
