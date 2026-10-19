package model;

public record CreateGameRequest(String authToken, String gameName) implements Request{
    public boolean hasNull(){
        return this.authToken == null || this.gameName == null;
    }
}