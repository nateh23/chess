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

        public calcUtility(ChessBoard board, ChessPosition position, ChessPiece piece){
            this.myBoard = board;
            this.myPosition = position;
            this.myPiece = piece;
        }

        public boolean checkForOpponent(int rowOffset, int colOffset){
            if (rowOffset == 0  && colOffset == 0) {
                throw new RuntimeException("Holy nothingburger");
            }

            int slideRowPos = this.myPosition.getRow() + rowOffset;
            int slideColPos = this.myPosition.getColumn() + colOffset;
            ChessPosition foundPos = new ChessPosition(slideRowPos,slideColPos);

            return slideColPos >= 1 && slideColPos <= 8 && slideRowPos >= 1 && slideRowPos <= 8 &&
                    (this.myBoard.getPiece(foundPos) != null && this.myBoard.getPiece(foundPos).getTeamColor() != this.myPiece.myColor);
        }

        public boolean isBlocked(int rowOffset, int colOffset){
            if (rowOffset == 0  && colOffset == 0) {
                throw new RuntimeException("Holy nothingburger");
            }

            int slideRowPos = this.myPosition.getRow() + rowOffset;
            int slideColPos = this.myPosition.getColumn() + colOffset;
            ChessPosition foundPos = new ChessPosition(slideRowPos,slideColPos);

            return slideColPos >= 1 && slideColPos <= 8 && slideRowPos >= 1 && slideRowPos <= 8 &&
                    (this.myBoard.getPiece(foundPos) != null);
        }

        public Collection<ChessMove> step(int rowOffset, int colOffset){
            if (rowOffset == 0  && colOffset == 0) {
                throw new RuntimeException("Holy nothingburger");
            }

            Collection<ChessMove> results = new ArrayList<>();

            int slideRowPos = this.myPosition.getRow() + rowOffset;
            int slideColPos = this.myPosition.getColumn() + colOffset;

            ChessPosition foundPos = new ChessPosition(slideRowPos,slideColPos);

            if (slideColPos >= 1 && slideColPos <= 8 && slideRowPos >= 1 && slideRowPos <= 8 &&
                    (this.myBoard.getPiece(foundPos) == null || this.myBoard.getPiece(foundPos).getTeamColor() != this.myPiece.myColor)){

                //if im a pawn and im at 8 or 1 i can be promoted here!
                if (this.myPiece.getPieceType() == PieceType.PAWN){
                    if ((this.myPiece.getTeamColor() == ChessGame.TeamColor.BLACK && foundPos.getRow() == 1) ||
                        (this.myPiece.getTeamColor() == ChessGame.TeamColor.WHITE && foundPos.getRow() == 8)
                    ){
                        results.add(new ChessMove(this.myPosition,foundPos,PieceType.BISHOP));
                        results.add(new ChessMove(this.myPosition,foundPos,PieceType.KING));
                        results.add(new ChessMove(this.myPosition,foundPos,PieceType.QUEEN));
                        results.add(new ChessMove(this.myPosition,foundPos,PieceType.ROOK));
                        results.add(new ChessMove(this.myPosition,foundPos,PieceType.KNIGHT));
                    }
                }else{
                    results.add(new ChessMove(this.myPosition,foundPos,null));
                }
            }

            return results;
        }

        public Collection<ChessMove> slide(int rowDir, int colDir){
            if (rowDir == 0  && colDir == 0) {
                throw new RuntimeException("Holy nothingburger");
            }

            Collection<ChessMove> results = new ArrayList<>();

            int slideRowPos = this.myPosition.getRow() + rowDir;
            int slideColPos = this.myPosition.getColumn() + colDir;

            while (slideColPos >= 1 && slideColPos <= 8 && slideRowPos >= 1 && slideRowPos <= 8){
                ChessPosition foundPos = new ChessPosition(slideRowPos,slideColPos);
                if (this.myBoard.getPiece(foundPos) == null){
                    results.add(new ChessMove(this.myPosition,foundPos,null));
                }else{
                    if (this.myBoard.getPiece(foundPos).getTeamColor() != this.myPiece.myColor){
                        results.add(new ChessMove(this.myPosition,foundPos,null));
                    }
                    break;
                }


                slideRowPos += rowDir;
                slideColPos += colDir;
            }

            return results;
        }

    }

    //brains
    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options.addAll(myUtil.slide(1,0));
        options.addAll(myUtil.slide(-1,0));
        options.addAll(myUtil.slide(0,1));
        options.addAll(myUtil.slide(0,-1));

        return options;
    }

    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options.addAll(myUtil.slide(1,1));
        options.addAll(myUtil.slide(-1,1));
        options.addAll(myUtil.slide(-1,-1));
        options.addAll(myUtil.slide(1,-1));

        return options;
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options.addAll(myUtil.slide(1,0));
        options.addAll(myUtil.slide(-1,0));
        options.addAll(myUtil.slide(0,1));
        options.addAll(myUtil.slide(0,-1));
        options.addAll(myUtil.slide(1,1));
        options.addAll(myUtil.slide(-1,1));
        options.addAll(myUtil.slide(-1,-1));
        options.addAll(myUtil.slide(1,-1));

        return options;
    }

    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options.addAll(myUtil.step(1,0));
        options.addAll(myUtil.step(-1,0));
        options.addAll(myUtil.step(0,1));
        options.addAll(myUtil.step(0,-1));
        options.addAll(myUtil.step(1,1));
        options.addAll(myUtil.step(-1,1));
        options.addAll(myUtil.step(-1,-1));
        options.addAll(myUtil.step(1,-1));

        return options;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        options.addAll(myUtil.step(2,-1));
        options.addAll(myUtil.step(1,-2));
        options.addAll(myUtil.step(2,1));
        options.addAll(myUtil.step(1,2));
        options.addAll(myUtil.step(-2,-1));
        options.addAll(myUtil.step(-1,-2));
        options.addAll(myUtil.step(-2,1));
        options.addAll(myUtil.step(-1,2));

        return options;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> options = new ArrayList<>();
        calcUtility myUtil = new calcUtility(board,myPosition,this);

        int directionMultiplier = (this.myColor == ChessGame.TeamColor.WHITE) ? 1 : -1;

        if (board.getPiece(new ChessPosition(myPosition.getRow() + directionMultiplier,myPosition.getColumn())) == null){
            if ((this.myColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) ||
                    (this.myColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7)
            ){
                options.addAll(myUtil.step(2 * directionMultiplier,0));
            }
        }

        if (!myUtil.isBlocked(directionMultiplier,0)){
            options.addAll(myUtil.step(directionMultiplier,0));
        }

        if (myUtil.checkForOpponent(directionMultiplier,-1)){
            options.addAll(myUtil.step(directionMultiplier,-1));
        }

        if (myUtil.checkForOpponent(directionMultiplier,1)){
            options.addAll(myUtil.step(directionMultiplier,1));
        }

        if ((this.myColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 5) ||
                (this.myColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 4)
        ){
            if (myUtil.checkForOpponent(0,-1)){
                options.addAll(myUtil.step(directionMultiplier,-1));
            }

            if (myUtil.checkForOpponent(0,1)){
                options.addAll(myUtil.step(directionMultiplier,1));
            }
        }

        return options;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> options;

        options = switch(this.myType){
            case PAWN -> pawnMoves(board,myPosition);
            case ROOK -> rookMoves(board,myPosition);
            case BISHOP -> bishopMoves(board,myPosition);
            case QUEEN -> queenMoves(board,myPosition);
            case KING -> kingMoves(board,myPosition);
            case KNIGHT -> knightMoves(board,myPosition);
            default -> throw new RuntimeException("This piece doesn't have a type?");
        };

        return options;
    }
}
