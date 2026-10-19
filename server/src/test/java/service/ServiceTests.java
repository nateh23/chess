package service;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {
    @Test
    @DisplayName("ApplicationServiceTests")
    public void applicationTest() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        ApplicationService applicationService = new ApplicationService(dataAccess);

        applicationService.clear();
    }

    @Test
    @DisplayName("ApplicationServiceTests")
    public void userTest() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        UserService userService = new UserService(dataAccess);

        RegisterRequest requestOne = new RegisterRequest("goat","idk","realemail");
        RegisterRequest requestTwo = new RegisterRequest(null,"idk","realemail");
        RegisterRequest requestThree = new RegisterRequest("cow",null,"realemail");
        RegisterRequest requestFour = new RegisterRequest("sheep","idk",null);

        RegisterResult registerResult = userService.register(requestOne);

        Assertions.assertAll(
                () -> Assertions.assertThrows(AlreadyTakenException.class, () -> userService.register(requestOne)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.register(requestTwo)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.register(requestThree)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.register(requestFour))
        );

        LogoutRequest logoutRequestOne = new LogoutRequest(registerResult.authToken());
        LogoutRequest logoutRequestTwo = new LogoutRequest(null);

        userService.logout(logoutRequestOne);

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.logout(logoutRequestTwo)),
                () -> Assertions.assertThrows(InvalidAuthToken.class, () -> userService.logout(logoutRequestOne))
        );

        LoginRequest loginRequestOne = new LoginRequest(requestOne.username(),requestOne.password());
        LoginRequest loginRequestTwo = new LoginRequest(null,"boo");
        LoginRequest loginRequestThree = new LoginRequest("yoo",null);
        LoginRequest loginRequestFour = new LoginRequest(requestOne.username(),"HAHAHAHA");
        LoginRequest loginRequestFive = new LoginRequest("veryRealGuy","HAHAHAHA");

        LoginResult loginResult = userService.login(loginRequestOne);

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.login(loginRequestTwo)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> userService.login(loginRequestThree)),
                () -> Assertions.assertThrows(InvalidCredentialsException.class, () -> userService.login(loginRequestFour)),
                () -> Assertions.assertThrows(InvalidCredentialsException.class, () -> userService.login(loginRequestFive))
        );

        userService.logout(new LogoutRequest(loginResult.authToken()));
    }

    @Test
    @DisplayName("GameServiceTests")
    public void gameServiceTest() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        GameService gameService = new GameService(dataAccess);
        UserService userService = new UserService(dataAccess);

        RegisterRequest registerOne = new RegisterRequest("one","one","one");
        RegisterRequest registerTwo = new RegisterRequest("two","two","two");
        RegisterRequest registerThree = new RegisterRequest("three","three","three");

        RegisterResult userResultOne = userService.register(registerOne);
        RegisterResult userResultTwo = userService.register(registerTwo);
        RegisterResult userResultThree = userService.register(registerThree);

        String badAuth = "imanevilauthandIhateeveryone";

        ListGamesRequest lgrOne = new ListGamesRequest(userResultOne.authToken());
        ListGamesRequest lgrTwo = new ListGamesRequest(null);
        ListGamesRequest lgrThree = new ListGamesRequest(badAuth);

        ListGamesResult gamesList = gameService.listGames(lgrOne);

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.listGames(lgrTwo)),
                () -> Assertions.assertThrows(InvalidAuthToken.class, () -> gameService.listGames(lgrThree))
        );

        CreateGameRequest cgrOne = new CreateGameRequest(null,"bruh");
        CreateGameRequest cgrTwo = new CreateGameRequest("yer",null);
        CreateGameRequest cgrThree = new CreateGameRequest(badAuth,"bruh");

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame(cgrOne)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame(cgrTwo)),
                () -> Assertions.assertThrows(InvalidAuthToken.class, () -> gameService.createGame(cgrThree))
        );

        CreateGameRequest goodRequest = new CreateGameRequest(userResultOne.authToken(),"pain");

        CreateGameResult gameOne = gameService.createGame(goodRequest);

        JoinGameRequest goodRequestOne = new JoinGameRequest(userResultOne.authToken(),"WHITE",1);
        JoinGameRequest goodRequestTwo = new JoinGameRequest(userResultTwo.authToken(),"WHITE",1);
        JoinGameRequest goodRequestThree = new JoinGameRequest(userResultTwo.authToken(),"BLACK",1);
        JoinGameRequest goodRequestFour = new JoinGameRequest(userResultThree.authToken(),"BLACK",1);

        JoinGameRequest nullRequestOne = new JoinGameRequest(null,"BLACK",1);
        JoinGameRequest nullRequestTwo = new JoinGameRequest("pride",null,1);
        JoinGameRequest nullRequestThree = new JoinGameRequest("pride","greed",0);

        JoinGameRequest evilAuthRequest = new JoinGameRequest(badAuth,"WHITE",1);
        JoinGameRequest fakeTeamRequest = new JoinGameRequest(userResultOne.authToken(),"GREEN",1);
        JoinGameRequest fakeGameIDRequest = new JoinGameRequest(userResultOne.authToken(),"WHITE",2);

        Assertions.assertAll(
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(nullRequestOne)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(nullRequestTwo)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(nullRequestThree)),
                () -> Assertions.assertThrows(InvalidAuthToken.class, () -> gameService.joinGame(evilAuthRequest)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(fakeTeamRequest)),
                () -> Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(fakeGameIDRequest))
        );

        gameService.joinGame(goodRequestOne);

        Assertions.assertThrows(InvalidTeamException.class, () -> gameService.joinGame(goodRequestTwo));

        gameService.joinGame(goodRequestThree);

        Assertions.assertThrows(InvalidTeamException.class, () -> gameService.joinGame(goodRequestFour));
    }
}
