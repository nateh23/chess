package service;

import dataaccess.AlreadyTakenException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;

import java.util.UUID;

public class UserService{
    DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, DataAccessException {
        UserData user = dataAccess.getUser(request.username());

        if (user != null){
            throw new AlreadyTakenException("Username already taken!");
        }

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new RegisterResult(newAuth.username(),newAuth.authToken());
    };
}
