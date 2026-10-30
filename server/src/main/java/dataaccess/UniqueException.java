package dataaccess;

public class UniqueException extends RuntimeException {
    public UniqueException(String message) {
        super(message);
    }
}
