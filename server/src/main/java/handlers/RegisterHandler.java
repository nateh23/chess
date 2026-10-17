package handlers;

import com.google.gson.Gson;

import io.javalin.http.Context;
import service.UserService;

import model.RegisterRequest;

public class RegisterHandler extends Handler {
    UserService service;

    public RegisterHandler(UserService service){
        this.service = service;
    }

    public void register(Context ctx){
        RegisterRequest request = new Gson().fromJson(ctx.body(),RegisterRequest.class);
        this.service.register(request);
    }
}
