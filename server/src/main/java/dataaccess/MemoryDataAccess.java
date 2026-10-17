package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryDataAccess implements DataAccess{
    final private HashMap<String, UserData> users = new HashMap<>();

    public UserData getUser(String username){
        return this.users.get(username);
    }
}
