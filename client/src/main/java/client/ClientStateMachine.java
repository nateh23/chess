package client;

import ui.EscapeSequences;

import java.util.Objects;

public class ClientStateMachine {
    public enum ClientState {
        PreLogin,
        PostLogin
    }

    String[][]preCommands = {
            {"register <USERNAME> <PASSWORD> <EMAL>","to create an account"},
            {"login <USERNAME> <PASSWORD>","to play chess"},
            {"quit","quit program"},
            {"help","list possible commands"}
    };

    private ClientState currentState;
    private ServerFacade facade;

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

    private String evalPrelogin(String line){
        if (Objects.equals(line, "quit")){
            return "quit";
        }else if(Objects.equals(line,"help")){
            printCommands(preCommands);
        }

        return "";
    }

    public String eval(String line){
        if (this.currentState == ClientState.PreLogin){
            return evalPrelogin(line);
        }else{

        }

        return "";
    }


    public ClientState getCurrentState(){
        return this.currentState;
    }


}
