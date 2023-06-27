package it.polimi.ingsw;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.Connection;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.interfaceType;
import it.polimi.ingsw.NETWORK.ServerHandler;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.util.Scanner;

public class SetUp {
    boolean active = true;
    boolean selectedCLI = false;
    public boolean selectedGUI = false;
    public boolean selectedCLIENT = false;
    public boolean selectedSERVER = false;
    public boolean selectedRMI = false;
    public boolean selectedTCP = false;
    public boolean crashed = false;

    public void run(String[] args) throws Exception {
        Scanner scanner;
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

        } while ( !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2") );

        do {
            System.out.println("************************************************************************************");
            System.out.println("Select:");
            System.out.println(" ( 0 ) if you want to quit");
            System.out.println(" ( 1 ) if you want to use the SERVER");
            System.out.println(" ( 2 ) if you want to use the CLIENT");

            scanner = new Scanner(System.in);
            playerInput = scanner.nextLine();
            scanner.reset();

            if (playerInput.equals("0")) {
                active = false;
            }
            if (playerInput.equals("1")) {
                selectedSERVER = true;
            }
            if ((playerInput.equals("2"))) {
                selectedCLIENT = true;
                System.out.println("************************************************************************************");
                System.out.println(" Insert the ip address of the Server: ( xxx.xxx.xxx.xxx ) ");
                playerInput = scanner.nextLine();
                scanner.reset();
                Settings.SERVER_NAME = playerInput;
                do {
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

                } while ( !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2") );
            }


        } while ( !playerInput.equals("0") && !playerInput.equals("1") && !playerInput.equals("2") );

        do{
            System.out.println("************************************************************************************");
            System.out.println("Select:");
            System.out.println(" ( 1 ) if ypu want to connect ");
            System.out.println(" ( 2 ) if you want to reconnect after the client crashed ");

            scanner = new Scanner(System.in);
            playerInput = scanner.nextLine();
            scanner.reset();

            if (playerInput.equals("1")) {
                crashed = false;
            }

            if (playerInput.equals("2")) {
                crashed = true;
            }

        }while(!playerInput.equals("1") && !playerInput.equals("2"));

        if (selectedCLI) {
            if (selectedSERVER) {
                ServerHandler server = new ServerHandler();
                server.run();
            }
            if (selectedCLIENT) {
                if (selectedTCP){
                    ClientTCP client = new ClientTCP(Settings.PORT_TCP, this.crashed);
                    CONTROLLER controller = new CONTROLLER(Connection.TCP , client, interfaceType.CLI);
                    client.controller = controller;
                    controller.startUserInterface(args);
                    client.start();
                }
                if(selectedRMI){
                    ClientRMI client = new ClientRMI(Settings.PORT_RMI, this.crashed);
                    CONTROLLER controller = new CONTROLLER(Connection.RMI, client, interfaceType.CLI);
                    client.controller = controller;
                    controller.startUserInterface(args);
                    client.start();
                }
            }
        }


        if(selectedGUI){
            if (selectedSERVER) {
                ServerHandler server = new ServerHandler();
                server.run();
            }
            if (selectedCLIENT) {
                if (selectedTCP){
                    ClientTCP client = new ClientTCP(Settings.PORT_TCP, crashed);
                    CONTROLLER controller = new CONTROLLER(Connection.TCP , client, interfaceType.GUI);
                    client.controller = controller;
                    client.start();
                    controller.startUserInterface(args);
                }
                if(selectedRMI) {
                    ClientRMI client = new ClientRMI(Settings.PORT_RMI, crashed);
                    CONTROLLER controller = new CONTROLLER(Connection.RMI , client, interfaceType.GUI);
                    client.controller = controller;
                    controller.startUserInterface(args);
                    client.start();
                }
            }
        }
    }
}
