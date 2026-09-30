package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard myBoard;
//    rule for dummyBoard, anytime we touch dummyboard it should be set to match myBoard at the end and beginning to
    // ensure it can be reused.
    private ChessBoard dummyBoard;
    TeamColor currentTeamTurn;

    /**
     * nates functions to make this not suck
     * im kinda curious if we can use the same pieces here, since pieces don't really care what pos or board they are on
     * like they could point to the same one.
     * also if this.... does nulls? it should
     */
    ////////// dummy board funcs
    private ChessPosition getKingDummyPosition(TeamColor color){
        for (int row = 1; row <= 8; row++){
            for (int column = 1; column <= 8; column++){
                ChessPosition calcPos = new ChessPosition(row,column);
                ChessPiece iterPiece = this.dummyBoard.getPiece(calcPos);
                if (iterPiece != null && iterPiece.getPieceType() == ChessPiece.PieceType.KING && iterPiece.getTeamColor() == color){
                    return calcPos;
                }
            }
        }
        return null;
    }

    private boolean isInDummyCheck(TeamColor teamColor) {
        TeamColor otherTeam = (teamColor == TeamColor.WHITE)? TeamColor.BLACK : TeamColor.WHITE;

        ChessPosition kingPosition = getKingDummyPosition(teamColor);
        Collection<ChessPosition> enemyTargets = findTargetSpots(otherTeam);

        return (enemyTargets.contains(kingPosition));
    }

    private void matchBoard(){
        if (this.myBoard == null){
            throw new RuntimeException("your board doesnt exist lb");
        }

        for (int row = 1; row <= 8; row++){
            for (int column = 1; column <= 8; column++){
                ChessPosition calcPos = new ChessPosition(row,column);
                this.dummyBoard.addPiece(calcPos, this.myBoard.getPiece(calcPos));
            }
        }
    }

    private void dummyMove(ChessMove move){
        if (this.myBoard == null){
            throw new RuntimeException("your board doesnt exist lb");
        }

        ChessPiece myPiece = this.dummyBoard.getPiece(move.getStartPosition());
        this.dummyBoard.addPiece(move.getEndPosition(),myPiece);
        this.dummyBoard.addPiece(move.getStartPosition(),null);
    }

    private HashSet<ChessPosition> findTargetSpots(TeamColor color){
        HashSet<ChessPosition> optionsAvailable = new HashSet<>();

        for (int row = 1; row <= 8; row++){
            for (int column = 1; column <= 8; column++){
                ChessPosition calcPos = new ChessPosition(row,column);
                ChessPiece pieceFound = this.dummyBoard.getPiece(calcPos);
                if (pieceFound != null && pieceFound.getTeamColor() == color){
                    Collection<ChessMove> pieceMoves = pieceFound.pieceMoves(this.dummyBoard,calcPos);
                    for (ChessMove move: pieceMoves){
                        optionsAvailable.add(move.getEndPosition());
                    }
                }
            }
        }

        return optionsAvailable;
    }
//////////

    private ChessPosition getKingPosition(TeamColor color){
        for (int row = 1; row <= 8; row++){
            for (int column = 1; column <= 8; column++){
                ChessPosition calcPos = new ChessPosition(row,column);
                ChessPiece iterPiece = this.myBoard.getPiece(calcPos);
                if (iterPiece != null && iterPiece.getPieceType() == ChessPiece.PieceType.KING && iterPiece.getTeamColor() == color){
                    return calcPos;
                }
            }
        }
        return null;
    }

    private Collection<ChessMove> getAllValidMoves(TeamColor color){
        Collection<ChessMove> options = new ArrayList<ChessMove>();

        for (int row = 1; row <= 8; row++){
            for (int column = 1; column <= 8; column++){
                ChessPosition calcPos = new ChessPosition(row,column);
                ChessPiece iterPiece = this.myBoard.getPiece(calcPos);
                if (iterPiece != null && iterPiece.getTeamColor() == color){
                    options.addAll(validMoves(calcPos));
                }
            }
        }

        return options;
    }

    public ChessGame() {
        this.dummyBoard = new ChessBoard();
        this.myBoard = new ChessBoard();
        this.myBoard.resetBoard();
        this.setTeamTurn(TeamColor.WHITE);
        this.matchBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.currentTeamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTeamTurn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(myBoard, chessGame.myBoard) && Objects.equals(dummyBoard, chessGame.dummyBoard) && currentTeamTurn == chessGame.currentTeamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myBoard, dummyBoard, currentTeamTurn);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> options = new ArrayList<ChessMove>();

        ChessPiece foundPiece = this.myBoard.getPiece(startPosition);
        if (foundPiece == null){
            return null;
        }

        Collection<ChessMove> unfilteredOptions = foundPiece.pieceMoves(this.myBoard,startPosition);

        for (ChessMove move: unfilteredOptions){
            matchBoard();
            dummyMove(move);
            if (isInDummyCheck(foundPiece.getTeamColor()) == false){
                options.add(move);
            }
        }

        return options;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece myPiece = this.myBoard.getPiece(move.getStartPosition());
        if (myPiece == null){
            throw new InvalidMoveException("There is no piece lb");
        }

        Collection<ChessMove> pieceMoves = validMoves(move.getStartPosition());

        if (pieceMoves.contains(move) && this.getTeamTurn() == myPiece.getTeamColor()){
            if (move.getPromotionPiece() != null){
                myBoard.addPiece(move.getEndPosition(),new ChessPiece(myPiece.getTeamColor(),move.getPromotionPiece()));
            }else{
                myBoard.addPiece(move.getEndPosition(),myPiece);
            }

            myBoard.addPiece(move.getStartPosition(),null);

            TeamColor otherTeam = (this.getTeamTurn() == TeamColor.WHITE)? TeamColor.BLACK : TeamColor.WHITE;
            this.setTeamTurn(otherTeam);
        }else{
            throw new InvalidMoveException("that was dumb");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        matchBoard();

        TeamColor otherTeam = (teamColor == TeamColor.WHITE)? TeamColor.BLACK : TeamColor.WHITE;

        ChessPosition kingPosition = getKingPosition(teamColor);
        Collection<ChessPosition> enemyTargets = findTargetSpots(otherTeam);

        return (enemyTargets.contains(kingPosition));
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (this.isInCheck(teamColor) && this.getAllValidMoves(teamColor).isEmpty());
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return (!this.isInCheck(teamColor) && this.getAllValidMoves(teamColor).isEmpty());
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.myBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.myBoard;
    }
}
