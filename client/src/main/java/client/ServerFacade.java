package client;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private String authToken;

    private ArrayList<GameData> gamesList = new ArrayList<>();

    private void verifyAuth(){
        if (authToken == null){
            throw new ResponseException("attempt to run command without auth");
        }
    }

    public ServerFacade(String url) {
        this.serverUrl = url;
    }

    public void clearDatabase(){
        var request = buildRequest("DELETE","/db",null);
        var response = sendRequest(request);

        handleResponse(response,null);
    }

    //user stuff
    public String registerUser(RegisterRequest requestData){
        var request = buildRequest("POST","/user",requestData);
        var response = sendRequest(request);

        RegisterResult properResult = handleResponse(response,RegisterResult.class);
        this.authToken = properResult.authToken();

        return properResult.username();
    }

    public String loginUser(LoginRequest requestData){
        var request = buildRequest("POST","/session",requestData);
        var response = sendRequest(request);

        LoginResult properResult = handleResponse(response,LoginResult.class);

        this.authToken = properResult.authToken();
        return properResult.username();
    }

    public void logout(){
        verifyAuth();

        var request = buildRequest("DELETE","/session",null);
        var response = sendRequest(request);

        handleResponse(response,null);
        this.authToken = null;
    }

    //games
    public ArrayList<GameData> listGames(){
        verifyAuth();
        var request = buildRequest("GET","/game",null);
        var response = sendRequest(request);

        ListGamesResult result =  handleResponse(response,ListGamesResult.class);

        gamesList = (ArrayList<GameData>) result.games();

        return gamesList;
    }

    public void createGame(String gameName){
        verifyAuth();
        var request = buildRequest("POST","/game",new CreateGameRequest(this.authToken,gameName));
        var response = sendRequest(request);

        handleResponse(response,CreateGameResult.class);
    }

    private GameData getGame(String gameListSpot){
        try {
            listGames(); //force update list to verify we have server list

            int gameNumber = Integer.parseInt(gameListSpot);
            return gamesList.get(gameNumber - 1);
        } catch (Exception e) {
            throw new RuntimeException("Client Error: Invalid game request");
        }
    }

    public ChessBoard observeGame(String gameListSpot) {
        verifyAuth();
        GameData game = getGame(gameListSpot);

        return game.game().getBoard();
    }

    public ChessBoard joinGame(String gameListSpot, String teamColorString){
        verifyAuth();
        GameData game = getGame(gameListSpot);

        if (!(Objects.equals(teamColorString, "WHITE") || Objects.equals(teamColorString, "BLACK"))){
            throw new RuntimeException("Client Error: Invalid team");
        }

        var request = buildRequest("PUT","/game",new JoinGameRequest(this.authToken,teamColorString,game.gameID()));
        var response = sendRequest(request);

        handleResponse(response,null);

        return game.game().getBoard();
    }

    //awesome helpers
    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }

        if (this.authToken != null){
            request.setHeader("authorization",authToken);
        }

        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }

            throw new ResponseException("Request Error " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
