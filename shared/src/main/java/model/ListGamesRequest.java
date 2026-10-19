package model;

public record ListGamesRequest(String authToken) implements Request{
    public boolean hasNull(){
        return this.authToken == null;
    }
}