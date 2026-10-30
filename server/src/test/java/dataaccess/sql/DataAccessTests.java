package dataaccess.sql;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.InvalidAuthToken;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

public class DataAccessTests {
    @Test
    @DisplayName("Coverage for DAO")
    public void daoTest() throws DataAccessException, InvalidMoveException {
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
//                () -> Assertions.assertThrows(DataAccessException.class, () -> dataAccess.getAuth(null))
        );

        dataAccess.deleteAuth(newAuth.authToken());
        dataAccess.deleteAuth(newAuth.authToken());

        Collection<GameData> games = dataAccess.listGames();

        dataAccess.createAuth(newAuth);

        dataAccess.addGameData(new GameData(1,null,null,"bruh", new ChessGame()));

        Collection<GameData> secondList = dataAccess.listGames();

        GameData test = dataAccess.getGameDataByID(1);
        Assertions.assertAll(
                () -> Assertions.assertNull(dataAccess.getGameDataByID(5))
        );

        UserData theUser = dataAccess.getUser(usernameOne);


        test.game().makeMove(new ChessMove(new ChessPosition(2,1), new ChessPosition(3,1),null));
        GameData bur = new GameData(1,usernameOne,null,"bruh", test.game());

        dataAccess.updateGame(1,bur);
    }
}
