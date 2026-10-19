package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidTeamException;
import model.*;

import java.util.Objects;

public class GameService extends Service{
    DataAccess dataAccess;
    int lowestGameId;

    public GameService(DataAccess dataAccess){
        super(dataAccess);
        this.dataAccess = dataAccess;
        this.lowestGameId = 1;
    }

    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException {
        validateNotNull(request);
        validateAuthToken(request.authToken());

        return new ListGamesResult(this.dataAccess.listGames());
    }

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
        validateNotNull(request);
        validateAuthToken(request.authToken());

        int gameId = this.lowestGameId;

        GameData gameData = new GameData(gameId,null,null, request.gameName(), new ChessGame());
        dataAccess.addGameData(gameData);

        this.lowestGameId += 1;

        return new CreateGameResult(gameData.gameID());
    }

    public void joinGame(JoinGameRequest request) throws DataAccessException{
        validateNotNull(request);
        validateAuthToken(request.authToken());

        GameData foundGame = dataAccess.getGameDataByID(request.gameID());

        if (foundGame == null){
            throw new DataAccessException("Invalid gameID");
        }

        if (!Objects.equals(request.playerColor(), "BLACK") && !Objects.equals(request.playerColor(), "WHITE")){
            throw new DataAccessException("Invalid Team");
        }

        if (
                (request.playerColor().equals("WHITE") && foundGame.whiteUsername() != null) ||
                (request.playerColor().equals("BLACK") && foundGame.blackUsername() != null)
        ){
            throw new InvalidTeamException("Team already taken");
        }

        String username = dataAccess.getAuth(request.authToken());

        GameData updatedGame;

        if (request.playerColor().equals("BLACK")){
            updatedGame = new GameData(foundGame.gameID(),foundGame.whiteUsername(),username,foundGame.gameName(),foundGame.game());
        }else{
            updatedGame = new GameData(foundGame.gameID(),username,foundGame.blackUsername(),foundGame.gameName(),foundGame.game());
        }

        dataAccess.updateGame(updatedGame.gameID(),updatedGame);
    }
}
