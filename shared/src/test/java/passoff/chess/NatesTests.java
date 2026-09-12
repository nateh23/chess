package passoff.chess;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import javax.swing.text.Utilities;

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
    @DisplayName("I guess we doin moves now")
    public void moveTest(){
        ChessBoard newBoard = new ChessBoard();
        newBoard.resetBoard();
        ChessPiece pawn = newBoard.getPiece(new ChessPosition(2,1));
        System.out.println(convertPieceToString(pawn));
    }

    public void presentBoard(ChessBoard board){
        for (int i = 0; i < 8; i++){
            System.out.println("___________________________________________________________________________");
            String boardLine = "";
            for (int a = 0; a < 8; a++){
                ChessPiece pieceFound = board.getPiece(new ChessPosition(8 - i,a + 1));
                String pieceValue = convertPieceToString(pieceFound);

                boardLine = boardLine + pieceValue + "   |   ";
            }
            System.out.println(boardLine);
        }
    }

    @Test
    @DisplayName("eternal misery")
    public void diff(){
        ChessBoard newBoard = new ChessBoard();
        newBoard.resetBoard();
        presentBoard(newBoard);
        System.out.println("YER");
        presentBoard(TestUtilities.defaultBoard());
    }
}
