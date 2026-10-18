package dataaccess;

public class InvalidAuthToken extends RuntimeException {
    public InvalidAuthToken(String message) {
        super(message);
    }
}
