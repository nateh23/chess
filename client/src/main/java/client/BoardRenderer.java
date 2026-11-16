package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

public class BoardRenderer {
    public BoardRenderer(){}
    String[] columns = {"a","b","c","d","e","f","g","h"};

    public void renderBoard(ChessBoard board, ChessGame.TeamColor perspective){
        boolean flipped = perspective != ChessGame.TeamColor.WHITE;
        renderColumnHelperRow(flipped);
        if (perspective == ChessGame.TeamColor.WHITE){
            for (int i = 8; i >= 1; i--){
                renderRow(board,i,flipped);
            }
        }else{
            for (int i = 1; i <= 8; i++){
                renderRow(board,i,flipped);
            }
        }
        renderColumnHelperRow(flipped);
    }

    private void setBackgroundColor(int row, int column){
        int flip = ((row % 2) + column) % 2;
        if (flip == 0){
            System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        }else{
            System.out.print(EscapeSequences.SET_BG_COLOR_WHITE);
        }
    }

    private void setPiece(ChessPiece piece){
        if (piece != null){
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
            }else{
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            }

            String icon = switch (piece.getPieceType()) {
                case ChessPiece.PieceType.ROOK -> "R";
                case ChessPiece.PieceType.KING -> "K";
                case ChessPiece.PieceType.PAWN -> "P";
                case ChessPiece.PieceType.QUEEN -> "Q";
                case ChessPiece.PieceType.BISHOP -> "B";
                case ChessPiece.PieceType.KNIGHT -> "N";
                default -> " ";
            };

            printPaddedString(icon);
        }else {
            System.out.print(EscapeSequences.EMPTY_LETTER);
        }
    }

    private void renderRow(ChessBoard board,int rowNumber, boolean flipped){
        setHelperColumn();
        printPaddedString(String.valueOf(rowNumber));

        if (flipped){
            for (int i = 8; i >= 1; i--){
                ChessPiece pieceAtLocation = board.getPiece(new ChessPosition(rowNumber,i));
                setBackgroundColor(rowNumber,i);
                setPiece(pieceAtLocation);
            }
        }else{
            for (int i = 1; i <= 8; i++){
                ChessPiece pieceAtLocation = board.getPiece(new ChessPosition(rowNumber,i));
                setBackgroundColor(rowNumber,i);
                setPiece(pieceAtLocation);
            }
        }

        setHelperColumn();
        printPaddedString(String.valueOf(rowNumber));

        resetColor();
        System.out.println();
    }

    private void renderColumnHelperRow(boolean flipped){
        setHelperColumn();

        System.out.print(EscapeSequences.EMPTY_LETTER);

        if (flipped){
            for (int i = columns.length - 1; i >= 0; i--){
                printPaddedString(columns[i]);
            }
        }else{
            for (int i = 0; i < columns.length; i++){
                printPaddedString(columns[i]);
            }
        }

        System.out.print(EscapeSequences.EMPTY_LETTER);

        resetColor();
        System.out.println();
    }

    private void resetColor(){
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
    }

    private void setHelperColumn(){
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
    }

    private void printPaddedString(String str){
        System.out.print(" " + str + " ");
    }
}
