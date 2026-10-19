package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.ListGamesRequest;
import model.ListGamesResult;
import service.GameService;
import io.javalin.http.Context;

public class ListGamesHandler extends Handler{
    GameService service;

    public ListGamesHandler(GameService service){
        this.service = service;
    }

    public void listGames(Context ctx) throws DataAccessException{
        String header = ctx.header("authorization");
        ListGamesRequest request = new ListGamesRequest(header);

        ListGamesResult result = this.service.listGames(request);

        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
}
