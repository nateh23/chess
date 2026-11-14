package client;

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

    public ClientState getCurrentState(){
        return this.currentState;
    }
}
