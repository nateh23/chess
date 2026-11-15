package client;

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
    public void sampleTest() {
//        Assertions.assertTrue(true);
//        facade.registerUser(new RegisterRequest("fred","fred","fred"));
//        facade.listGames();
//        facade.createGame("yes");
//
//        System.out.print("\u001b[31;42m");
//        System.out.printf("Merry Christmas");
    }

}
