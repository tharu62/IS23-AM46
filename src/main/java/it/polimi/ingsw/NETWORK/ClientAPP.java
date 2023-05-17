package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CLI.CLI;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.GUI.GUI;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.util.Scanner;

public class ClientAPP {

    public static void main(String[] args){
        String input;
        CONTROLLER controller = new CONTROLLER();
        ClientTCP clientTCP  = null;
        ClientRMI clientRMI = null;
        CLI cli = null;
        GUI gui = null;
        do {
            System.out.print("Select what you want to start (CLI or GUI): ");
            Scanner in = new Scanner(System.in);
            input = in.next();
            input = input.toUpperCase();
            if (input.equals("CLI")) {
                cli = new CLI();
                if(cli.startCLI().equals("RMI")) clientRMI = new ClientRMI();
                else clientTCP = new ClientTCP(controller);
            } else if (input.equals("GUI")) {
                gui = new GUI();
            }
        } while (!input.equals("CLI") && !input.equals("RMI"));

        if (cli != null) {
            controller.username = cli.askUsername();

        }
        if (gui != null) {

        }

    }
}