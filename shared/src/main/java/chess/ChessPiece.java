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
    //util that will be resued by other pieces
    private class calcUtility{
        ChessBoard myBoard;
        ChessPosition myPosition;
        ChessPiece myPiece;

        public calcUtility(ChessBoard board, ChessPosition position, ChessPiece piece){
            this.myBoard = board;
            this.myPosition = position;
            this.myPiece = piece;
        }


    }

    //brains
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition){
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options;
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options = myUtil.rookSet();

        return options;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
//        Collection<ChessMove> options;
//
//        options = switch(this.myType){
//            case PAWN -> pawnMoves(board,myPosition);
//            case ROOK -> rookMoves(board,myPosition);
//            default -> throw new RuntimeException("This piece doesn't have a type?");
//        };
//
//        return options;
    }
}
