package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor myColor;
    private ChessPiece.PieceType myType;
    private boolean firstMove;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.myColor = pieceColor;
        this.myType = type;
        this.firstMove = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return myColor == that.myColor && myType == that.myType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myColor, myType);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.myColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.myType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    //we read it here 1-8
    private class moveCalculator{
        ChessBoard myBoard;
        ChessPiece myPiece;
        int [][][] pieceWorld;

        public moveCalculator(ChessBoard board, ChessPiece piece, ChessPosition myPosition){
            this.myBoard = board;
            this.myPiece = piece;

            //gonna make a board relative to the piece that points to the overall spots
            int currentRow = myPosition.getRow();
            int relativeRank = (piece.myColor == ChessGame.TeamColor.WHITE) ? currentRow : 8 - currentRow;

            System.out.println(relativeRank);
        }
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition){
        moveCalculator myCalculator = new moveCalculator(board,this,myPosition);
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        throw new RuntimeException("Not implemented");
    }
}
