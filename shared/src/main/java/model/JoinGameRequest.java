package model;

public record JoinGameRequest(String authToken, String playerColor, int gameID) implements Request{
    public boolean hasNull(){
        return this.authToken == null | this.playerColor == null | this.gameID == 0;
    }
}