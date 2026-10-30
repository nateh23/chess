package dataaccess.sql;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SQLDataAccess implements DataAccess {
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
             `password` varchar(256) NOT NULL,
             `email` varchar(256) NOT NULL,
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
    private static final Logger log = LoggerFactory.getLogger(SQLDataAccess.class);

    public SQLDataAccess(){
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement: statements){
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email from users WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String dataUser = rs.getString("username");
                        String passwordHashed = rs.getString("password");
                        String email = rs.getString("email");

                        return new UserData(dataUser,passwordHashed,email);
                    }
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }

        return null;
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO auth (authToken, username) VALUES (?,?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,authData.authToken());
                ps.setString(2,authData.username());
                ps.executeUpdate();
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, password, email) VALUES (?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,userData.username());
                ps.setString(2,userData.password());
                ps.setString(3,userData.email());
                ps.executeUpdate();
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public String getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username from auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("username");
                    }
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }

        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,authToken);
                ps.executeUpdate();
            }
        }catch (SQLException ex){
            log.error("e: ", ex);
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public void clearAllData() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "DROP TABLE auth, users, games";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
                configureDatabase();
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM games";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var json = rs.getString("json");
                        GameData game = new Gson().fromJson(json,GameData.class);
                        list.add(game);
                    }
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
        return list;
    }

    public void addGameData(GameData gameData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO games (gameID, json) VALUES (?,?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(1,gameData.gameID());
                ps.setString(2, new Gson().toJson(gameData));
                ps.executeUpdate();
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }

    public GameData getGameDataByID(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json from games WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(1,gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        var json = rs.getString("json");
                        return new Gson().fromJson(json,GameData.class);
                    }
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }

        return null;
    }

    public void updateGame(int gameID, GameData gameData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "UPDATE games SET json=? WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(2,gameData.gameID());
                ps.setString(1, new Gson().toJson(gameData));
                ps.executeUpdate();
            }
        }catch (SQLException ex){
            throw new DataAccessException("SQL Connection Failed", ex);
        }
    }
}
