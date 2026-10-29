package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.List;

public class SQLDataAccess implements DataAccess{
    private static final String[] statements = {
            """
             CREATE TABLE IF NOT EXISTS auth (
             `authToken` varchar(256) NOT NULL,
             `username` varchar(256) NOT NULL,
             PRIMARY KEY (`authToken`),
             INDEX(username)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
             CREATE TABLE IF NOT EXISTS users (
             `username` varchar(256) NOT NULL,
             `json` TEXT NOT NULL,
             PRIMARY KEY (`username`)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
             CREATE TABLE IF NOT EXISTS games (
             `gameID` int NOT NULL,
             `json` TEXT NOT NULL,
             PRIMARY KEY (`gameID`)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLDataAccess(){

    }

    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    public void createAuth(AuthData authData) throws DataAccessException {

    }

    public void createUser(UserData userData) throws DataAccessException {

    }

    public String getAuth(String authToken) throws DataAccessException {
        return "";
    }

    public void deleteAuth(String authToken) throws DataAccessException {

    }

    public void clearAllData() throws DataAccessException {

    }

    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    public void addGameData(GameData gameData) throws DataAccessException {

    }

    public int getGamesCount() throws DataAccessException {
        return 0;
    }

    public GameData getGameDataByID(int gameId) throws DataAccessException {
        return null;
    }

    public void updateGame(int gameId, GameData gameData) throws DataAccessException {

    }
}
