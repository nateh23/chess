package client;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private String authToken;

    private void verifyAuth(){
        if (authToken == null){
            throw new RuntimeException("attempt to run command without auth");
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
    }

    //games
    public ListGamesResult listGames(){
        verifyAuth();
        var request = buildRequest("GET","/game",null);
        var response = sendRequest(request);

        return handleResponse(response,ListGamesResult.class);
    }

    public CreateGameResult createGame(String gameName){
        verifyAuth();
        var request = buildRequest("GET","/game",new CreateGameRequest(this.authToken,gameName));
        var response = sendRequest(request);

        return handleResponse(response,CreateGameResult.class);
    }

    public void joinGame(String gameName){
        verifyAuth();
        var request = buildRequest("GET","/game",new CreateGameRequest(this.authToken,gameName));
        var response = sendRequest(request);

        handleResponse(response,null);
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
