package it.polimi.ingsw;

import it.polimi.ingsw.CLI.CLI_Interface;
import it.polimi.ingsw.NETWORK.ServerHandler;

import java.rmi.RemoteException;
import java.util.Scanner;
public class SetUpper {
    boolean active = true;
    boolean selectedCLI = false;
    public boolean selectedGUI = false;
    public boolean selectedCLIENT = false;
    public boolean selectedSERVER = false;
    public boolean selectedRMI = false;
    public boolean selectedTCP = false;

    public void run() throws RemoteException, InterruptedException {
        Scanner scanner = null;
        String playerInput;

        do {
            System.out.println("************************************************************************************");
            System.out.println("Select:");
            System.out.println(" ( 0 ) if you want to quit");
            System.out.println(" ( 1 ) if you want to use the SetUpper");
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

        if(selectedSERVER) {
            ServerHandler server = new ServerHandler();
            server.run();
        }
    }
}
