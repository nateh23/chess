package service;

import dataaccess.DataAccess;
import model.RegisterRequest;

public class UserService{
    DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void register(RegisterRequest request){

    };
}
