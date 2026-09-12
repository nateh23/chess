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

        public void debugViewSet(Collection<ChessMove>options){
            int[][]board = new int[8][8];

            board[myPosition.getRow()][myPosition.getColumn()] = 3;

            for (ChessMove move: options){
                ChessPosition endPos = move.getEndPosition();
                board[endPos.getRow()][endPos.getColumn()] = 1;
            }

            for (int b = 7; b > -1; b--){
                System.out.println("__________________________");
                String soFar = "";
                for (int a = 7; a > -1; a--){
                    int value = board[b][a];
                    soFar = soFar + value + "||";
                }

                System.out.println(soFar);
            }
        }

        public Collection<ChessMove> rookSet(){
            Collection<ChessMove> options = new ArrayList<>();

            //cast up
            for (int i = this.myPosition.getRow() + 1; i < 8; i++){
                ChessPosition newSpot = new ChessPosition(i,this.myPosition.getColumn());

                if (this.myBoard.getPiece(newSpot) == null){
                    options.add(new ChessMove(this.myPosition,newSpot,null));
                }else {
                    break;
                }
            }

            //cast down
            for (int i = this.myPosition.getRow() - 1; i > 0; i--){
                ChessPosition newSpot = new ChessPosition(i,this.myPosition.getColumn());
                System.out.println("checking" + i);
                if (this.myBoard.getPiece(newSpot) == null){
                    System.out.println("check");
                    options.add(new ChessMove(this.myPosition,newSpot,null));
                }else {
                    break;
                }
            }

            return options;
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
        myUtil.debugViewSet(options);

        return myUtil.rookSet();
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
