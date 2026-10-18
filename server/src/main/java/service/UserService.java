package service;

import dataaccess.*;
import model.*;

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

        dataAccess.createUser(new UserData(request.username(),request.password(),request.email()));

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new RegisterResult(newAuth.username(),newAuth.authToken());
    };

    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserData user = dataAccess.getUser(request.username());

        if (user == null){
            throw new DataAccessException("User does not exist");
        }

        if (user.password() == request.password()){
            System.out.println(user.password());
            System.out.println(request.password());
            System.out.println("bad password");
            throw new InvalidPasswordException("Password does not match!");
        }

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new LoginResult(newAuth.username(),newAuth.authToken());
    };

    public void logout(LogoutRequest request) throws DataAccessException{
        String username = dataAccess.getAuth(request.authToken());

        if(username == null){
            throw new InvalidAuthToken("Auth token does not exist");
        }

        dataAccess.deleteAuth(request.authToken());
    }
}
