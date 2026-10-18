package model;

public record LogoutRequest(String authToken) implements Request{
    public boolean hasNull(){
        return this.authToken == null;
    }
}
