package server;

import com.google.gson.Gson;
import dataaccess.*;
import handlers.ClearHandler;
import handlers.LoginHandler;
import handlers.LogoutHandler;
import io.javalin.*;
import handlers.RegisterHandler;
import service.ApplicationService;
import service.UserService;

import java.util.Map;

public class Server {
    private final Javalin javalin;


    public Server() {
        //db
        final MemoryDataAccess dataAccess = new MemoryDataAccess();
        //services
        final UserService userService = new UserService(dataAccess);
        final ApplicationService applicationService = new ApplicationService(dataAccess);
        //handlers
        final ClearHandler clearHandler = new ClearHandler(applicationService);

        final RegisterHandler registerHandler = new RegisterHandler(userService);
        final LoginHandler loginHandler = new LoginHandler(userService);
        final LogoutHandler logoutHandler = new LogoutHandler(userService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user",registerHandler::register);
        javalin.post("/session",loginHandler::login);
        javalin.delete("/session",logoutHandler::logout);
        javalin.delete("/db",clearHandler::clear);

        //exceptions
        javalin.exception(AlreadyTakenException.class, (e, ctx) -> {
           ctx.status(403).result(new Gson().toJson((Map.of("message","Error: already taken"))));
        });

        javalin.exception(DataAccessException.class, (e, ctx) -> {
            ctx.status(400).result(new Gson().toJson((Map.of("message","Error: bad request"))));
        });

        javalin.exception(InvalidCredentialsException.class, (e, ctx) -> {
            ctx.status(401).result(new Gson().toJson((Map.of("message","Error: unauthorized"))));
        });

        javalin.exception(InvalidAuthToken.class, (e, ctx) -> {
            ctx.status(401).result(new Gson().toJson((Map.of("message","Error: unauthorized"))));
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
