package client;

import ui.EscapeSequences;

import java.util.Scanner;

public class Client {
    ClientStateMachine ClientStateLogic;

    public Client(int port){
        this.ClientStateLogic = new ClientStateMachine("http://localhost:" + port);
    }

    public void boot(){
        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            result = this.ClientStateLogic.eval(line);

            System.out.println();
        }
    }

    public void printPrompt(){
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        if (this.ClientStateLogic.getCurrentState() == ClientStateMachine.ClientState.PreLogin){
            System.out.print("\n + [PRELOGIN] >>> ");
        }else{
            System.out.print("\n + [POSTLOGIN] >>> ");
        }
        resetColor();
    }

    private void resetColor(){
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
    }
}
