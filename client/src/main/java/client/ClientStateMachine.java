package client;

import chess.ChessBoard;
import chess.ChessGame;
import model.GameData;
import model.LoginRequest;
import model.RegisterRequest;
import ui.EscapeSequences;

import java.util.ArrayList;
import java.util.Objects;

public class ClientStateMachine {
    public enum ClientState {
        PreLogin,
        PostLogin
    }

    String[][]preCommands = {
            {"register <USERNAME> <PASSWORD> <EMAIL>","to create an account"},
            {"login <USERNAME> <PASSWORD>","to play chess"},
            {"quit","quit program"},
            {"help","list possible commands"}
    };

    String[][]postCommands = {
            {"logout","logout user"},
            {"list","to see all chess games"},
            {"create <GAME NAME>","to create a new chess game"},
            {"join <GAME ID> <COLOR>","to join a game"},
            {"observe <GAME ID>","to observe a game"},
            {"help","list possible commands"}
    };

    private ClientState currentState;
    private ServerFacade facade;
    private BoardRenderer renderer;

    public String cleanErrors(String code){
        return switch(code){
            case "param" -> "Error: not enough arguments given";
            default -> "Invalid Command; use help to see commands";
        };
    }

    public ClientStateMachine(String url){
        this.currentState = ClientState.PreLogin;
        this.facade = new ServerFacade(url);
        this.renderer = new BoardRenderer();
    }

    private void printCommands(String[][]commands){
        for (int i = 0; i < commands.length; i++){
            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            System.out.print(EscapeSequences.EMPTY + commands[i][0] + ":");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print(" " + commands[i][1]);
            System.out.println();
        }
    }

    private String joinGame(String[]givenCommand){
        if (givenCommand.length >= 3){
            ChessBoard board = facade.joinGame(givenCommand[1],givenCommand[2]);

            ChessGame.TeamColor colorView = Objects.equals(givenCommand[2], "WHITE") ?
                    ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

            renderer.renderBoard(board, colorView);
            return "";
        }else {
            return cleanErrors("param");
        }
    }

    private String observeGame(String[]givenCommand){
        if (givenCommand.length >= 2){
            ChessBoard board = facade.observeGame(givenCommand[1]);
            renderer.renderBoard(board, ChessGame.TeamColor.WHITE);
            return "";
        }else {
            return cleanErrors("param");
        }
    }

    private String createGame(String[]givenCommand){
        if (givenCommand.length >= 2){
            facade.createGame(givenCommand[1]);

            return "Successfully create game " + givenCommand[1];
        }else {
            return cleanErrors("param");
        }
    }

    private void listGames(){
        ArrayList<GameData> gamesList = facade.listGames();
        int i = 0;
        for (GameData game: gamesList){
            i += 1;
            System.out.print(i + ".");
            System.out.print("  Game name:" + game.gameName());
            System.out.print("  White:" + (game.whiteUsername() != null ? game.whiteUsername() : "Empty"));
            System.out.print("  Black:" + (game.blackUsername() != null ? game.blackUsername() : "Empty"));
            System.out.println();
        }
    }

    private String logout(){
        facade.logout();
        currentState = ClientState.PreLogin;
        return "Successfully logged out.";
    }

    private String register(String[]givenCommand){
        if (givenCommand.length >= 4){
                String username = facade.registerUser(new RegisterRequest(
                        givenCommand[1],
                        givenCommand[2],
                        givenCommand[3]
                ));

                currentState = ClientState.PostLogin;
                return "Successfully registered as " + username + ".";
        }else {
            return cleanErrors("param");
        }
    }

    private String login(String[]givenCommand){
        if (givenCommand.length >= 3){
            String username = facade.loginUser(new LoginRequest(
                    givenCommand[1],
                    givenCommand[2]
            ));

            currentState = ClientState.PostLogin;
            return "Successfully logged in as " + username + ".";
        }else {
            return cleanErrors("param");
        }
    }

    private String evalPrelogin(String[]givenCommand){
        return switch (givenCommand[0]) {
            case "quit" -> "quit";
            case "help" -> {
                printCommands(preCommands);
                yield "";
            }
            case "register" -> register(givenCommand);
            case "login" -> login(givenCommand);
            default -> cleanErrors("rip");
        };
    }

    private String evalPostLogin(String[]givenCommand){
        return switch (givenCommand[0]) {
            case "logout" -> logout();
            case "help" -> {
                printCommands(postCommands);
                yield "";
            }
            case "list" -> {
                listGames();
                yield "";
            }
            case "create" -> createGame(givenCommand);
            case "join" -> joinGame(givenCommand);
            case "observe" -> observeGame(givenCommand);
            default -> cleanErrors("rip");
        };
    }

    public String eval(String line){
        String[]givenCommand = line.split(" ");

        try {
            if (this.currentState == ClientState.PreLogin){
                return evalPrelogin(givenCommand);
            }else{
                return evalPostLogin(givenCommand);
            }
        }catch (Exception ex){
            return ex.getMessage() != null ? ex.getMessage() : "Server Error: Failed to connect to server";
        }
    }


    public ClientState getCurrentState(){
        return this.currentState;
    }
}
