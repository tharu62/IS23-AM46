package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.RemoteException;
import java.util.Scanner;

public class CLI extends Thread {
    public CONTROLLER controller;
    public CLI_commands cmd;
    public CLI(CONTROLLER controller , ClientRMI client) throws InterruptedException {
        this.controller = controller;
        cmd = new CommandsExecutor(controller , client);
    }

    public CLI(CONTROLLER controller , ClientTCP client) throws InterruptedException {
        this.controller = controller;
        cmd = new CommandsExecutor(controller , client);
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String StrCommand;
        boolean inputNotValid;
        boolean chatInputInProgress = false;
        boolean gameplayInputInProgress = false;
        System.out.println("******************************************************************************************");
        System.out.println(" WELCOME TO MY SHELFIE ONLINE GAME (CLI VERSION)");
        while (true) {
            inputNotValid = true;
            if(controller.getGameIsOver()){
                break;
            }
            if(controller.getMyTurn() && !chatInputInProgress) {
                cmd.printBoard(controller.grid);
                cmd.printCommonGoals(controller.cards, controller.token_value);
                cmd.printBookshelf(controller.bookshelf);
                cmd.printScore();
                cmd.printActions();
                StrCommand = in.nextLine();
                gameplayInputInProgress = true;
                if (StrCommand.equalsIgnoreCase("shutdown")) {
                    System.out.println(" Goodbye! ");
                    System.exit(0);
                }

                if (StrCommand.equalsIgnoreCase("chat")) {
                    try {
                        gameplayInputInProgress = cmd.sendChat();

                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if( StrCommand.equalsIgnoreCase("draw") && controller.draw_end){
                    System.out.println(" YOU HAVE DRAWN 3 ITEM! YOU CAN'T DRAW MORE. NOW YOU HAVE TO PUT YOUR DRAW IN YOUR BOOKSHELF! ");
                }
                if (StrCommand.equalsIgnoreCase("draw") && !controller.draw_end) {
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
                    gameplayInputInProgress = false;
                    inputNotValid = false;
                }

                if ( StrCommand.equalsIgnoreCase("put")) {
                    try {

                        cmd.putDraw();
                        if(cmd.replyPut()){
                            System.out.println(" PUT VALID ");
                            cmd.endTurn();
                            cmd.replyEndTurn();
                            controller.myTurn = false;
                        }
                        else{
                            System.out.println(" PUT NOT VALID, RETRY. ");
                        }
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    gameplayInputInProgress = false;
                    inputNotValid = false;
                }

                if(StrCommand.equalsIgnoreCase("common goal")){
                    cmd.printCommonGoals(controller.cards, controller.token_value);
                    gameplayInputInProgress = false;
                    inputNotValid = false;
                }

                if(StrCommand.equalsIgnoreCase("personal goal")){
                    try {
                        cmd.printPersonalGoal();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    gameplayInputInProgress = false;
                    inputNotValid = false;
                }

                if(inputNotValid){
                    System.out.println(" Command not valid, retry. ");
                    gameplayInputInProgress = false;
                }
            }else{
                if(controller.LoginOK && !gameplayInputInProgress) {
                    chatInputInProgress = true;
                    if (controller.chatBuffer.size() > 0) {
                        //stampa i messaggi salvati in buffer, nel mentre cancella il contenuto del buffer.
                        while(controller.chatBuffer.size() > 0) {
                            cmd.printChatBuffer();
                        }
                    }
                    cmd.printActionsChat();
                    StrCommand = in.nextLine();
                    if (StrCommand.equalsIgnoreCase("chat")) {
                        try {
                            cmd.sendChat();
                            chatInputInProgress = false;
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        inputNotValid = false;
                    }
                    if(StrCommand.equalsIgnoreCase("play")){
                        chatInputInProgress = false;
                        inputNotValid = false;
                    }
                    if (inputNotValid) {
                        System.out.println(" Command not valid, retry. ");
                    }
                }
            }
        }

        int counter = 0;
        while(true) {
            System.out.println(" IF YOU WANT TO PLAY AGAIN RESTART THE APP. ");
            System.out.println(" (0) QUIT OR CLOSE THE APP ");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            if (input == 0) {
                System.exit(0);
            }else{
                if(counter == 4 ){
                    System.out.println(" I GIVE UP... ");
                    counter++;
                }
                if(counter == 3 ){
                    System.out.println(" WHAT IS WRONG WITH YOU? DO YOU LIKE TO WASTE YOUR TIME? ");
                    counter++;
                }
                if(counter == 2 ){
                    System.out.println(" DUDE.... JUST QUIT ALREADY... ");
                    counter++;
                }
                if(counter == 1 ){
                    System.out.println(" COME ONE YOU HAVE TO TYPE '0' ");
                    counter++;
                }
                if(counter == 0 ){
                    System.out.println(" I KNOW YOU DON'T WANT TO QUIT THIS AMAZING GAME BUT THERE IS NOTHING ELSE TO DO. ");
                    counter++;
                }
            }
        }

    }
}
