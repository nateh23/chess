package client;

import java.util.Objects;

public class ClientStateMachine {
    public enum ClientState {
        PreLogin,
        PostLogin
    }

    private ClientState currentState;
    private ServerFacade facade;

    public ClientStateMachine(String url){
        this.currentState = ClientState.PreLogin;
        this.facade = new ServerFacade(url);
    }

    public String evalPrelogin(String line){
        if (Objects.equals(line, "quit")){
            return "quit";
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
