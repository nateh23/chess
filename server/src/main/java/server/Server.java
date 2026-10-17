package server;

import io.javalin.*;
import handlers.RegisterHandler;
import service.UserService;

public class Server {
    private final Javalin javalin;


    public Server() {
        //services
        final UserService userService = new UserService();
        //handlers
        final RegisterHandler registerHandler = new RegisterHandler(userService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user",registerHandler::register);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
