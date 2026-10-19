package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.CreateGameRequest;
import model.GameData;
import model.ListGamesResult;
import model.ListGamesRequest;

import java.util.UUID;

public class GameService extends Service{
    DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        super(dataAccess);
        this.dataAccess = dataAccess;
    }

    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException {
        validateNotNull(request);
        validateAuthToken(request.authToken());

        return new ListGamesResult(this.dataAccess.listGames());
    }

    public CreateGameRequest createGame(CreateGameRequest request) throws DataAccessException{
        validateNotNull(request);
        validateAuthToken(request.authToken());

        int gameIdCounter = 0;

        if (dataAccess.getGamesCount() >= Integer.MAX_VALUE - 1){
            throw new DataAccessException("No free ID's");
        }

        GameData gameData = new GameData(,"","", request.gameName());
    }
}
