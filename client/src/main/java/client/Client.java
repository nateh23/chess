package client;

public class Client {
    ClientStateMachine ClientState;

    public Client(int port){
        this.ClientState = new ClientStateMachine("http://localhost:" + port);
    }

    private void presentBoard(){

    }
}
