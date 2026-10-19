package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.CreateGameRequest;
import model.CreateGameResult;
import service.GameService;
import io.javalin.http.Context;

public class CreateGameHandler extends Handler{
    GameService service;

    public CreateGameHandler(GameService service){
        this.service = service;
    }

    public void createGame(Context ctx) throws DataAccessException {
        String header = ctx.header("authorization");
        CreateGameRequest bodyRequest = new Gson().fromJson(ctx.body(), CreateGameRequest.class);

        CreateGameRequest request = new CreateGameRequest(
                header,
                bodyRequest.gameName() == null ? null : bodyRequest.gameName());

        CreateGameResult result = this.service.createGame(request);

        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
}
