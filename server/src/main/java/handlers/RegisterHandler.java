package handlers;

import com.google.gson.Gson;

import dataaccess.DataAccessException;

import io.javalin.http.Context;

import service.UserService;

import model.RegisterRequest;
import model.RegisterResult;

public class RegisterHandler extends Handler {
    UserService service;

    public RegisterHandler(UserService service){
        this.service = service;
    }

    public void register(Context ctx) throws DataAccessException {
        RegisterRequest request = new Gson().fromJson(ctx.body(),RegisterRequest.class);

        RegisterResult result = this.service.register(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
}
