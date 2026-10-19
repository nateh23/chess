package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidAuthToken;

public class Service {
    DataAccess dataAccess;

    Service(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void validateAuthToken(String authToken) throws DataAccessException {
        String username = this.dataAccess.getAuth(authToken);

        if(username == null){
            throw new InvalidAuthToken("Auth token does not exist");
        }
    }
}
