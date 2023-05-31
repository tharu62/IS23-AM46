package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.RemoteException;
import java.util.Scanner;

public class CLI extends Thread  {
    CONTROLLER controller;
    public CLI_Interface cmd;
    public CLI(CONTROLLER controller , ClientRMI client) throws InterruptedException {
        this.controller = controller;
        cmd = new CLI_methods(controller , client);
    }

    public CLI(CONTROLLER controller , ClientTCP client) throws InterruptedException {
        this.controller = controller;
        cmd = new CLI_methods(controller , client);
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String StrCommand;
        boolean inputNotValid = true;
        int counter = 0;
        System.out.println("******************************************************************************************");
        System.out.println(" WELCOME TO MY SHELFIE ONLINE GAME (CLI VERSION)");
        while (true) {
            if(controller.getMyTurn()) {
                cmd.printActions();
                StrCommand = in.nextLine();

                if (StrCommand.equalsIgnoreCase("shutdown")) {
                    System.out.println(" Goodbye! ");
                    System.exit(0);
                }

                if (StrCommand.equalsIgnoreCase("chat")) {
                    try {
                        cmd.sendChat();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if( StrCommand.equalsIgnoreCase("draw") && controller.draw_end){
                    System.out.println(" YOU HAVE DRAWN 3 ITEM! YOU CAN'T DRAW MORE. NOW YOU HAVE TO PUT YOUR DRAW IN YOUR BOOKSHELF! ");
                }
                if (!controller.draw_end && StrCommand.equalsIgnoreCase("draw")) {
                    try {
                        cmd.askDraw();
                        if(cmd.replyDraw()){
                            System.out.println(" DRAW VALID. ");
                            controller.drawStatus++;
                            if (controller.drawStatus == 3) {
                                controller.draw_end = true;
                            }
                        }else{
                            System.out.println(" DRAW NOT VALID, RETRY. ");
                        }
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if ( StrCommand.equalsIgnoreCase("put")) {
                    try {
                        cmd.putDraw();
                        if(cmd.replyPut()){
                            System.out.println(" PUT VALID ");
                            cmd.updateBookshelf();
                            controller.put_end = true;
                            controller.end_turn = true;
                        }
                        else{
                            System.out.println(" PUT NOT VALID, RETRY. ");
                        }
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if(StrCommand.equalsIgnoreCase("common goal")){
                    cmd.printCommonGoals(controller.cards);
                    inputNotValid = false;
                }

                if(StrCommand.equalsIgnoreCase("personal goal")){
                    try {
                        cmd.printPersonalGoal(controller.getPersonalGoal());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if(controller.end_turn && StrCommand.equalsIgnoreCase("end turn")){
                    try {
                        cmd.endTurn();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }
                if(inputNotValid){
                    System.out.println(" Command not valid, retry. ");
                }
            }
        }
    }
}
