package model;

public record LoginRequest(String username, String password) implements Request{
    public boolean hasNull(){
        return this.username == null | this.password == null;
    }
}
