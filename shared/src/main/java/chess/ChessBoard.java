package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] myBoard;

    private ChessPiece genPiece(String notatedForm){
        ChessGame.TeamColor color = (notatedForm.charAt(0) == 'w') ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
        ChessPiece.PieceType type;

        char piece = notatedForm.charAt(1);

        type = switch (piece) {
            case 'r' -> ChessPiece.PieceType.ROOK;
            case 'k' -> ChessPiece.PieceType.KING;
            case 'p' -> ChessPiece.PieceType.PAWN;
            case 'q' -> ChessPiece.PieceType.QUEEN;
            case 'b' -> ChessPiece.PieceType.BISHOP;
            case 'n' -> ChessPiece.PieceType.KNIGHT;
            default -> throw new RuntimeException("Not a real piece");
        };

        return new ChessPiece(color,type);
    }

    private void generateBoard(){
        this.myBoard = new ChessPiece[8][8];
    }

    public ChessBoard() {
        generateBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.myBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) { //THIS ACCOUNTS FOR EVERYTHING BEING SET FROM 1 TO 8, NOT 0 TO 7
        if (position.getRow() > 8 || position.getColumn() > 8){
            return null;
        }

        return this.myBoard[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i = 0; i < 8; i++){
            for (int a = 0; a < 8; a++){
                this.myBoard[i][a] = null;
            }
        }

        String[] specialsTemplate = {"r","n","b","q","k","b","n","r"};

        //first white specials, then pawns
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(1,i),genPiece("w" + specialsTemplate[i - 1]));
        }
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(2,i),genPiece("wp"));
        }

        //now black
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(8,i),genPiece("b" + specialsTemplate[i - 1]));
        }
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(7,i),genPiece("bp"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(myBoard, that.myBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(myBoard);
    }
}
