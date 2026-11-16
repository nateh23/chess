package client;

import ui.EscapeSequences;

import java.util.Objects;
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

            if (!Objects.equals(result, "quit") && !Objects.equals(result, "")){
                System.out.println(result);
            }

            System.out.println();
        }
    }

    public void printPrompt(){
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        if (this.ClientStateLogic.getCurrentState() == ClientStateMachine.ClientState.PreLogin){
            System.out.print("[PRELOGIN] >>> ");
        }else{
            System.out.print("[POSTLOGIN] >>> ");
        }
        resetColor();
    }

    private void resetColor(){
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
    }
}
