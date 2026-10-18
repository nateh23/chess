package handlers;

import com.google.gson.Gson;

import dataaccess.DataAccessException;

import model.LoginResult;
import service.UserService;
import io.javalin.http.Context;

import model.LoginRequest;

public class LoginHandler {
    UserService service;

    public LoginHandler(UserService service){
        this.service = service;
    }

    public void login(Context ctx) throws DataAccessException{
        LoginRequest request = new Gson().fromJson(ctx.body(), LoginRequest.class);

        LoginResult result = this.service.login(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
}
