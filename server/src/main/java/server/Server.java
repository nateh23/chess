package server;

import com.google.gson.Gson;
import dataaccess.*;
import handlers.*;
import io.javalin.*;
import service.ApplicationService;
import service.GameService;
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
        final GameService gameService = new GameService(dataAccess);
        //handlers
        final ClearHandler clearHandler = new ClearHandler(applicationService);

        final RegisterHandler registerHandler = new RegisterHandler(userService);
        final LoginHandler loginHandler = new LoginHandler(userService);
        final LogoutHandler logoutHandler = new LogoutHandler(userService);

        final ListGamesHandler listGamesHandler = new ListGamesHandler(gameService);
        final CreateGameHandler createGameHandler = new CreateGameHandler(gameService);
        final JoinGameHandler joinGameHandler = new JoinGameHandler(gameService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.post("/user",registerHandler::register);
        javalin.post("/session",loginHandler::login);
        javalin.delete("/session",logoutHandler::logout);
        javalin.delete("/db",clearHandler::clear);
        javalin.get("/game",listGamesHandler::listGames);
        javalin.post("/game",createGameHandler::createGame);
        javalin.put("/game",joinGameHandler::joinGame);

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

        javalin.exception(InvalidTeamException.class, (e, ctx) -> {
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
