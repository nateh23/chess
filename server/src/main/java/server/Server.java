package server;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.MemoryDataAccess;
import io.javalin.*;
import handlers.RegisterHandler;
import service.UserService;

import java.util.Map;

public class Server {
    private final Javalin javalin;


    public Server() {
        //db
        final MemoryDataAccess dataAccess = new MemoryDataAccess();
        //services
        final UserService userService = new UserService(dataAccess);
        //handlers
        final RegisterHandler registerHandler = new RegisterHandler(userService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user",registerHandler::register);


        //exceptions
        javalin.exception(AlreadyTakenException.class, (e, ctx) -> {
           ctx.status(403).result(new Gson().toJson((Map.of("message","Error: already taken"))));
        });
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
