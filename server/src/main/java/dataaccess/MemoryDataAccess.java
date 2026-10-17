package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryDataAccess implements DataAccess{
    final private HashMap<String, UserData> users = new HashMap<>();
    final private HashMap<String, String> authTokens = new HashMap<>();

    public UserData getUser(String username){
        return this.users.get(username);
    }

    public void createAuth(AuthData authData){
        this.authTokens.put(authData.authToken(),authData.username());
    }

    public void createUser(UserData userData){
        users.put(userData.username(),userData);
    }
}
