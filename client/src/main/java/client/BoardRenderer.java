package client;

import chess.ChessBoard;
import chess.ChessGame;
import ui.EscapeSequences;

public class BoardRenderer {
    public BoardRenderer(){}
    String[] columns = {"a","b","c","d","e","f","g","h"};

    public void RenderBoard(ChessBoard board, ChessGame.TeamColor perspective){
        renderColumnHelperRow(perspective == ChessGame.TeamColor.WHITE);
    }

    private void renderColumnHelperRow(boolean flipped){
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);

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

    private void printPaddedString(String str){
        System.out.print(" " + str + " ");
    }
}
