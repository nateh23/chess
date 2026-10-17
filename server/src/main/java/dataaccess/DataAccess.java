package dataaccess;

import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;

public interface DataAccess {
    UserData getUser(String username) throws DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException;

    void createUser(UserData userData) throws DataAccessException;
}
