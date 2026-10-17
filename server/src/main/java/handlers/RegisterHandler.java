package handlers;

import io.javalin.http.Context;
import service.UserService;

public class RegisterHandler extends Handler {
    UserService service;

    public RegisterHandler(UserService service){
        this.service = service;
    }

    public void register(Context ctx){

    }
}
