package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidAuthToken;
import model.Request;

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

    public void validateNotNull(Request request) throws DataAccessException {
        if (request.hasNull()){
            throw new DataAccessException("Invalid request");
        }
    }
}
