package client;

import java.util.Scanner;

public class Client {
    ClientStateMachine ClientState;

    public Client(int port){
        this.ClientState = new ClientStateMachine("http://localhost:" + port);
    }

    public void boot(){
        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")){
            String line = scanner.nextLine();

            System.out.println(line);
        }
    }

    private void presentBoard(){

    }
}
