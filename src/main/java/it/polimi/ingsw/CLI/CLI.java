package it.polimi.ingsw.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.TCP.ClientTCP;
import java.util.Scanner;
public class CLI implements CLI_Interface{
    boolean active = true;
    boolean selectedCLI = false;
    public boolean selectedGUI = false;
    public boolean selectedCLIENT = false;
    public boolean selectedSERVER = false;
    public boolean selectedRMI = false;
    public boolean selectedTCP = false;

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


    public void input_loop() {
        Scanner scanner = null;
        String playerInput;

        do {
            System.out.println("************************************************************************************");
            System.out.println("Select:");
            System.out.println(" ( 0 ) if you want to quit");
            System.out.println(" ( 1 ) if you want to use the CLI");
            System.out.println(" ( 2 ) if you want to use the GUI");
            scanner = new Scanner(System.in);
            playerInput = scanner.nextLine();
            scanner.reset();

            if (playerInput.equals("0")) {
                active = false;
            }
            if ((playerInput.equals("1"))) {
                selectedCLI = true;
            }
            if (playerInput.equals("2")) {
                selectedGUI = true;
            }

        }while(playerInput!=null && !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2"));

        do {
            System.out.println("************************************************************************************");
            System.out.println("Select:");
            System.out.println(" ( 0 ) if you want to quit");
            System.out.println(" ( 1 ) if you want to use the CLIENT");
            System.out.println(" ( 2 ) if you want to use the SERVER");
            scanner = new Scanner(System.in);
            playerInput = scanner.nextLine();
            scanner.reset();

            if (playerInput.equals("0")) {
                active = false;
            }
            if ((playerInput.equals("1"))) {
                do {
                    selectedCLIENT = true;
                    System.out.println("************************************************************************************");
                    System.out.println("Select:");
                    System.out.println(" ( 0 ) if you want to quit");
                    System.out.println(" ( 1 ) if you want to use the CONNECTION_RMI");
                    System.out.println(" ( 2 ) if you want to use the CONNECTION_TCP");
                    scanner = new Scanner(System.in);
                    playerInput = scanner.nextLine();
                    scanner.reset();

                    if (playerInput.equals("0")) {
                        active = false;
                    }
                    if ((playerInput.equals("1"))) {
                        selectedRMI = true;
                    }
                    if (playerInput.equals("2")) {
                        selectedTCP = true;
                    }

                }while(playerInput!=null && !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2"));
            }
            if (playerInput.equals("2")) {
                selectedSERVER = true;
            }

        }while(playerInput!=null && !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2"));


        if(selectedCLI){
            if(selectedCLIENT){
                while(active){

                }
            }
            if(selectedSERVER){
                while(active){

                }
            }
        }

    }

    @Override
    public void adapt(String cmd) {

    }
}
