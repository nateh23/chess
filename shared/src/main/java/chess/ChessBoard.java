package chess;

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
        String[] specialsTemplate = {"r","n","b","k","q","b","n","r"};

        //first white specials, then pawns
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPosition(i + 1,1),genPiece("w" + specialsTemplate[i]));
        }
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPosition(i + 1,2),genPiece("wp"));
        }

        //now black
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPosition(i + 1,8),genPiece("b" + specialsTemplate[i]));
        }
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPosition(i + 1,7),genPiece("bp"));
        }
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
    public ChessPiece getPiece(ChessPosition position) {
        return this.myBoard[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.myBoard = null;
        generateBoard();
    }
}
