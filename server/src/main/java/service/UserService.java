package service;

import dataaccess.*;
import model.*;

import java.util.Objects;
import java.util.UUID;

public class UserService extends Service{
    DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        super(dataAccess);
        this.dataAccess = dataAccess;
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, DataAccessException {
        if (request.hasNull()){
            throw new DataAccessException("Invalid request");
        }

        UserData user = dataAccess.getUser(request.username());

        if (user != null){
            throw new AlreadyTakenException("Username already taken!");
        }

        dataAccess.createUser(new UserData(request.username(),request.password(),request.email()));

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new RegisterResult(newAuth.username(),newAuth.authToken());
    };

    public LoginResult login(LoginRequest request) throws DataAccessException {
        if (request.hasNull()){
            throw new DataAccessException("Invalid request");
        }

        UserData user = dataAccess.getUser(request.username());

        if (user == null){
            throw new InvalidCredentialsException("User does not exist");
        }

        if (!Objects.equals(user.password(), request.password())){
            throw new InvalidCredentialsException("Password does not match!");
        }

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new LoginResult(newAuth.username(),newAuth.authToken());
    };

    public void logout(LogoutRequest request) throws DataAccessException{
        if (request.hasNull()){
            throw new DataAccessException("Invalid request");
        }

        validateAuthToken(request.authToken());

        dataAccess.deleteAuth(request.authToken());
    }
}
