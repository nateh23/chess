package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.CreateGameResult;
import model.JoinGameRequest;
import service.GameService;

public class JoinGameHandler extends Handler{
    GameService service;

    public JoinGameHandler(GameService service){
        this.service = service;
    }

    public void joinGame(Context ctx) throws DataAccessException {
        String header = ctx.header("authorization");
        JoinGameRequest bodyRequest = new Gson().fromJson(ctx.body(), JoinGameRequest.class);

        JoinGameRequest request = new JoinGameRequest(
                header,
                bodyRequest.playerColor() == null ? null : bodyRequest.playerColor(),
                bodyRequest.gameID() == 0 ? 0 : bodyRequest.gameID());

        this.service.joinGame(request);

        ctx.status(200);
    }
}
