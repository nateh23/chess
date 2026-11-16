package client;

import dataaccess.DataAccessException;
import dataaccess.InvalidAuthToken;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
        facade.clearDatabase();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void coverageTest() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(ResponseException.class, () -> facade.listGames()),
                () -> Assertions.assertThrows(ResponseException.class, () ->
                        facade.loginUser(new LoginRequest("tom", "tom"))),
                () -> Assertions.assertThrows(ResponseException.class, () -> facade.logout())
        );

        facade.registerUser(new RegisterRequest("fred","fred","fred"));
        facade.listGames();
        facade.createGame("yes");
        facade.joinGame("1","WHITE");

        Assertions.assertAll(
                () -> Assertions.assertThrows(ResponseException.class, () ->
                        facade.registerUser(new RegisterRequest("fred","fred","fred"))),
                () -> Assertions.assertThrows(ResponseException.class, () ->
                        facade.joinGame("2","WHITE")),
                () -> Assertions.assertThrows(ResponseException.class, () ->
                        facade.joinGame("1","GREEN")),
                () -> Assertions.assertThrows(ResponseException.class, () ->
                        facade.observeGame("2"))
        );

        facade.logout();

        facade.registerUser(new RegisterRequest("tom","tom","tom"));
        facade.logout();
        facade.loginUser(new LoginRequest("tom", "tom"));
        facade.createGame("no");
        facade.observeGame("2");
        facade.joinGame("1","BLACK");
    }

}
