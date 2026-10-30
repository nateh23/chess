package dataaccess;

import java.sql.SQLException;

public class DatabaseConnectionError extends RuntimeException {
    public DatabaseConnectionError(String message, SQLException ex) {
        super(message);
    }
}
