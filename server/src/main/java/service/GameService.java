package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.ListGamesResult;
import model.ListGamesRequest;

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
}
