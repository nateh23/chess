package model;

public record RegisterRequest(String username, String password, String email) implements Request{
    public boolean hasNull(){
        return this.username == null | this.password == null | this.email == null;
    }
}
