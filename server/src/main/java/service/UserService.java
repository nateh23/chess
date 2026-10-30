package service;

import dataaccess.*;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.UUID;

public class UserService extends Service{
    DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        super(dataAccess);
        this.dataAccess = dataAccess;
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, DataAccessException {
        validateNotNull(request);

        UserData user = dataAccess.getUser(request.username());

        if (user != null){
            throw new AlreadyTakenException("Username already taken!");
        }

        String hashedPassword = BCrypt.hashpw(request.password(),BCrypt.gensalt());

        dataAccess.createUser(new UserData(request.username(),hashedPassword,request.email()));

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new RegisterResult(newAuth.username(),newAuth.authToken());
    };

    public LoginResult login(LoginRequest request) throws DataAccessException {
        validateNotNull(request);

        UserData user = dataAccess.getUser(request.username());

        if (user == null){
            throw new InvalidCredentialsException("User does not exist");
        }

        if (!BCrypt.checkpw(request.password(),user.password())){
            throw new InvalidCredentialsException("Password does not match!");
        }

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        dataAccess.createAuth(newAuth);

        return new LoginResult(newAuth.username(),newAuth.authToken());
    };

    public void logout(LogoutRequest request) throws DataAccessException{
        validateNotNull(request);
        validateAuthToken(request.authToken());

        dataAccess.deleteAuth(request.authToken());
    }
}
