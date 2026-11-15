package client;

import model.RegisterRequest;
import ui.EscapeSequences;

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

    private ClientState currentState;
    private ServerFacade facade;

    public String cleanErrors(String code){
        return switch(code){
            case "param" -> "Error: not enough arguments given";
            default -> "Invalid Command; use help to see commands";
        };
    }

    public ClientStateMachine(String url){
        this.currentState = ClientState.PreLogin;
        this.facade = new ServerFacade(url);
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

    private String register(String[]givenCommand){
        if (givenCommand.length >= 4){
            try {
                String username = facade.registerUser(new RegisterRequest(
                        givenCommand[1],
                        givenCommand[2],
                        givenCommand[3]
                ));
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }else {
            return cleanErrors("param");
        }

        return "";
    }

    private String evalPrelogin(String[]givenCommand){
        if (Objects.equals(givenCommand[0], "quit")){
            return "quit";
        }else if(Objects.equals(givenCommand[0],"help")){
            printCommands(preCommands);
        }else if(Objects.equals(givenCommand[0],"register")){
            return register(givenCommand);
        }else {
            return cleanErrors("rip");
        }

        return "";
    }

    public String eval(String line){
        String[]givenCommand = line.split(" ");

        if (this.currentState == ClientState.PreLogin){
            return evalPrelogin(givenCommand);
        }else{

        }

        return cleanErrors("rip");
    }


    public ClientState getCurrentState(){
        return this.currentState;
    }


}
