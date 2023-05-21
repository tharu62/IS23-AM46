package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;
import java.util.List;
import java.util.Scanner;

public class CLI extends Thread implements CLI_Interface {
    CONTROLLER controller;

    public CLI(CONTROLLER controller) {
        this.controller = controller;
    }
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String StrCommand;
        boolean inputNotValid = true;
        System.out.println("******************************************************************************************");
        System.out.println(" WELCOME TO MY SHELFIE ONLINE GAME (CLI VERSION)");
        while (true) {
            if(controller.myTurn) {
                System.out.println("******************************************************************************************");
                System.out.println(" Actions: ");
                System.out.println(" (shutdown) shutdown the APP ");
                System.out.println(" (chat)     chat with players ");
                System.out.println(" (draw)     draw a tiles from Board ");
                System.out.println(" (put)      put tiles in your Bookshelf ");
                StrCommand = in.nextLine();
                if (StrCommand.equalsIgnoreCase("shutdown")) {
                    System.out.println(" Goodbye! ");
                    System.exit(0);
                }
                if (StrCommand.equalsIgnoreCase("chat")) {
                    sendChat();
                    inputNotValid = false;
                }
                if (StrCommand.equalsIgnoreCase("draw")) {
                    //TODO
                    inputNotValid = false;
                }
                if (StrCommand.equalsIgnoreCase("put")) {
                    //TODO
                    inputNotValid = false;
                }
                if(inputNotValid){
                    System.out.println(" Command not valid, retry. ");
                    System.out.println("******************************************************************************************");
                    System.out.println(" Actions: ");
                    System.out.println(" (shutdown) shutdown the APP ");
                    System.out.println(" (chat)     chat with players ");
                    System.out.println(" (draw)     draw a tiles from Board ");
                    System.out.println(" (put)      put tiles in your Bookshelf ");
                }
            }
        }
    }

    @Override
    public void notify(String message) {
        System.out.println("******************************************************************************************");
        System.out.println(message);
    }

    @Override
    public String getUsername() {
        System.out.println( " Insert username:  ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }

    @Override
    public int getLobbySize() {
        System.out.println( " Insert Lobby size:  ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();

    }

    @Override
    public void printBoard(item[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                switch (grid[i][j]) {
                    case CATS:
                        String redBG = Color.CATS_GREEN_BG.escape();
                        System.out.print(redBG + "\u001B[30m" + " C " + Color.RESET);
                        break;
                    case PLANTS:
                        String greenBG = Color.PLANTS_RED_BG.escape();
                        System.out.print(greenBG + "\u001B[30m" + " P " + Color.RESET);
                        break;
                    case FRAMES:
                        String blueBG = Color.FRAMES_BLUE_BG.escape();
                        System.out.print(blueBG + "\u001B[30m" + " F " + Color.RESET );
                        break;
                    case TROPHIES:
                        String yellowBG = Color.TROPHIES_CYAN_BG.escape();
                        System.out.print(yellowBG + "\u001B[30m" + " T " + Color.RESET);
                        break;
                    case BOOKS:
                        String magentaBG = Color.BOOKS_WHITE_BG.escape();
                        System.out.print(magentaBG + "\u001B[30m" + " B " + Color.RESET);
                        break;
                    case GAMES:
                        String cyanBG = Color.GAMES_YELLOW_BG.escape();
                        System.out.print(cyanBG + "\u001B[30m" + " G " + Color.RESET);
                        break;
                }
            }
            System.out.println();
        }
    }

    @Override
    public void printPersonalGoal(PERSONAL_GOAL_CARD personalGoalCard) {
        PrintPersonalGoals printPersonalGoals = new PrintPersonalGoals();
        int id = personalGoalCard.getCardLogic().getId();
        System.out.println("    0 | 1 | 2 | 3 | 4 ");
        System.out.println("  ╔═══╦═══╦═══╦═══╦═══╗");
        printPersonalGoals.printGoal(id);
        System.out.println("  ╚═══╩═══╩═══╩═══╩═══╝");
    }

    @Override
    public void printCommonGoals(List<COMMON_GOAL_CARD> commonGoalCards) {
        PrintCommonGoals printCommonGoals = new PrintCommonGoals();
        int id1 = commonGoalCards.get(0).getCardLogic().getId();
        int id2 = commonGoalCards.get(1).getCardLogic().getId();
        printCommonGoals.printCommon(id1);
        printCommonGoals.printCommon(id2);
    }

    @Override
    public void sendChat() {
        System.out.println(" Insert text: ");
        Scanner in = new Scanner(System.in);
        String StrCommand = in.nextLine();
        controller.sendChat(StrCommand);
    }


}
