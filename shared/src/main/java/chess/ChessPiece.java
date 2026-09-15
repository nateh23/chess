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

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.myColor = pieceColor;
        this.myType = type;
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
        PieceType promotionPiece = null;

        public calcUtility(ChessBoard board, ChessPosition position, ChessPiece piece, PieceType promotion){
            this.myBoard = board;
            this.myPosition = position;
            this.myPiece = piece;
            this.promotionPiece = promotion;
        }

        public Collection<ChessMove> slide(int rowDir, int colDir){
            if (rowDir == 0  && colDir == 0) {
                throw new RuntimeException("Holy nothingburger");
            }

            Collection<ChessMove> results = new ArrayList<>();

            int slideRowPos = this.myPosition.getRow() + rowDir;
            int slideColPos = this.myPosition.getColumn() + colDir;

            System.out.println("starting row: " + slideRowPos + "; starting col: " + slideColPos);

            while (slideColPos >= 1 && slideColPos <= 8 && slideRowPos >= 1 && slideRowPos <= 8){
                System.out.println("checking row: " + slideRowPos + "; checking col: " + slideColPos);
                if (this.myBoard.getPiece(new ChessPosition(slideRowPos,slideColPos)) == null){
                    System.out.println("DING DING DING");
                    results.add(new ChessMove(this.myPosition,new ChessPosition(slideRowPos,slideColPos),this.promotionPiece));
                }

                slideRowPos += rowDir;
                slideColPos += colDir;
            }
            return results;
        }

    }

    //brains
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition){
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this,null);

        options.addAll(myUtil.slide(1,0));

        return options;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> options;

        options = switch(this.myType){
            case PAWN -> pawnMoves(board,myPosition);
            case ROOK -> rookMoves(board,myPosition);
            default -> throw new RuntimeException("This piece doesn't have a type?");
        };

        return options;
    }
}
