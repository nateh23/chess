package handlers;

import com.google.gson.Gson;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.LoginResult;
import model.LogoutRequest;
import service.UserService;


public class LogoutHandler extends Handler{
    UserService service;

    public LogoutHandler(UserService service){
        this.service = service;
    }

    public void logout(Context ctx) throws DataAccessException{
        String header = ctx.header("authorization");
        LogoutRequest request = new LogoutRequest(header);

        this.service.logout(request);
        ctx.status(200);
    }
}
