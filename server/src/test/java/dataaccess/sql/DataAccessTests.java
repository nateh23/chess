package dataaccess.sql;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidAuthToken;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class DataAccessTests {
    @Test
    @DisplayName("Coverage for DAO")
    public void daoTest() throws DataAccessException {
        DataAccess dataAccess = new SQLDataAccess();
        dataAccess.clearAllData();

        String usernameOne = "yeah";
        String usernameTwo = "nope";

        String passwordOne = "huh";
        String passwordTwo = "uhu";

        String email = "wtv";

        Assertions.assertNull(dataAccess.getUser(usernameOne));

        dataAccess.createUser(new UserData(usernameOne,passwordOne,email));

        AuthData newAuth = new AuthData(UUID.randomUUID().toString(),usernameOne);
        AuthData badAuth = new AuthData(UUID.randomUUID().toString(),usernameTwo);

        dataAccess.createAuth(newAuth);
        dataAccess.getAuth(newAuth.authToken());

        Assertions.assertNull(dataAccess.getAuth(badAuth.authToken()));

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> dataAccess.createUser(new UserData(usernameOne,passwordOne,email))),
                () -> Assertions.assertThrows(DataAccessException.class, () -> dataAccess.createAuth(newAuth))
        );
    }
}
