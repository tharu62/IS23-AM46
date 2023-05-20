package it.polimi.ingsw.VIEW.CLI;

import java.util.Scanner;

public class CLI extends Thread implements CLI_Interface {
    boolean something = false;

    @Override
    public void run() {
        while (true) {
            if (something) {
                System.out.println(" Shutdown... ");
                break;
            }
            //TODO input cases from player...
        }
    }

    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    @Override
    public String getUsername() {
        System.out.println(" Type your Username: ");
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
    }

    @Override
    public int getLobbySize() {
        int playerNumber;
        System.out.println(" How many players are going to play? (You can choose between 2, 3 or 4 players): ");
        try {
            playerNumber = numberInput(2, 3, 4, null);
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
    }



}
