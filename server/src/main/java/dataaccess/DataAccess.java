package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface DataAccess {
    UserData getUser(String username) throws DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException;

    void createUser(UserData userData) throws DataAccessException;

    String getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    void clearAllData() throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;
}
