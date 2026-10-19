package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidTeamException;
import model.*;

import java.util.Objects;
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

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
        validateNotNull(request);
        validateAuthToken(request.authToken());

        int gameId = 1;

        if (dataAccess.getGamesCount() >= Integer.MAX_VALUE - 1){
            throw new DataAccessException("No free ID's");
        }

        for (int i = 1; i <= Integer.MAX_VALUE - 1; i++){
            GameData foundGame = dataAccess.getGameDataByID(i);

            if (foundGame == null){
                gameId = i;
                break;
            }
        }

        GameData gameData = new GameData(gameId,null,null, request.gameName(), new ChessGame());
        dataAccess.addGameData(gameData);

        return new CreateGameResult(gameData.gameId());
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
                (request.playerColor().equals("BLACK") && foundGame.whiteUsername() != null)
        ){
            throw new InvalidTeamException("Team already taken");
        }
    }
}
