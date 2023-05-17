package it.polimi.ingsw.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.TCP.ClientTCP;

import java.util.Scanner;

public class CLI {
    /**
     * This method asks the player if he wants to connect to the server using RMI or TCP
     */
    public String startCLI() {
        System.out.println("\nMy Shelfie");
        String input;
        while (true) {
            System.out.println("Do you want to connect via RMI or TCP?");
            Scanner in = new Scanner(System.in);
            input = in.next();
            input = input.toUpperCase();
            if (input.equals("TCP")) {
                System.out.println("Connection using TCP...");
                return "TCP";
            }
            if (input.equals("RMI")) {
                System.out.println("Connection using RMI...");
                return "RMI";
            }
        }
    }

    public String askUsername() {
        String input;
        System.out.println("Insert your username: ");
        Scanner in = new Scanner(System.in);
        input = in.next();
        input = input.toUpperCase();
        return input;
    }
}
