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
    public void ApplicationTest() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        ApplicationService applicationService = new ApplicationService(dataAccess);

        applicationService.clear();
    }

    @Test
    @DisplayName("ApplicationServiceTests")
    public void UserTest() throws DataAccessException {
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
}
