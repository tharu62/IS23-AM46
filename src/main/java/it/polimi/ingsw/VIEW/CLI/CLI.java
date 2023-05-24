package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
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
            if(controller.getMyTurn()) {
                printActions();
                StrCommand = in.nextLine();

                if (StrCommand.equalsIgnoreCase("shutdown")) {
                    System.out.println(" Goodbye! ");
                    System.exit(0);
                }

                if (StrCommand.equalsIgnoreCase("chat")) {
                    try {
                        sendChat();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if (StrCommand.equalsIgnoreCase("draw")) {
                    while(true){
                        try {
                            if(askDraw()){
                                System.out.println(" DRAW VALID. ");
                                System.out.println(" KEEP DRAWING OR PUT THE ITEMS IN YOUR BOOKSHELF. ");
                                break;
                            }else{
                                System.out.println(" DRAW NOT VALID, RETRY. ");
                            }
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    inputNotValid = false;
                }

                if (StrCommand.equalsIgnoreCase("put")) {
                    try {
                        if(putDraw()){
                            updateBookshelf();
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }
                
                if(inputNotValid){
                    System.out.println(" Command not valid, retry. ");
                    printActions();
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
                colorTile(grid, i, j);
            }
            System.out.println();
        }
    }

    @Override
    public void printPersonalGoal(int personalGoalCard) {
        PrintPersonalGoals printPersonalGoals = new PrintPersonalGoals();
        System.out.println("    0 | 1 | 2 | 3 | 4 ");
        System.out.println("  ╔═══╦═══╦═══╦═══╦═══╗");
        printPersonalGoals.printGoal(personalGoalCard);
        System.out.println("  ╚═══╩═══╩═══╩═══╩═══╝");
    }

    @Override
    public void printCommonGoals(List<Integer> commonGoalCards) {
        PrintCommonGoals printCommonGoals = new PrintCommonGoals();
        int id1 = commonGoalCards.get(0);
        int id2 = commonGoalCards.get(1);
        printCommonGoals.printCommon(id1);
        printCommonGoals.printCommon(id2);
    }

    @Override
    public void sendChat() throws RemoteException {
        System.out.println(" Insert text: ");
        Scanner in = new Scanner(System.in);
        String StrCommand = in.nextLine();
        System.out.println(" Insert receiver: ");
        String receiver = in.nextLine();
        controller.sendChat(StrCommand, receiver);
    }

    @Override
    public boolean askDraw() throws RemoteException {
        int row = 0;
        int col = 0;
        System.out.println(" Insert x and y value of the item you want to draw from the Board :");
        System.out.println(" row : ");
        Scanner in = new Scanner(System.in);
        row = in.nextInt();
        while(row < 0 || row > 8){
            System.out.println(" Draw out of bound!!! You can insert only a number between 0 and 9. ");
            System.out.println(" row : ");
            in = new Scanner(System.in);
            row = in.nextInt();
        }
        System.out.println(" col : ");
        in = new Scanner(System.in);
        col = in.nextInt();
        while(col < 0 || col > 8){
            System.out.println(" Draw out of bound!!! You can only insert a number between 0 and 9. ");
            System.out.println(" col : ");
            in = new Scanner(System.in);
            col = in.nextInt();
        }
        return controller.setDraw(row,col);
    }

    @Override
    public boolean putDraw() throws RemoteException {
        int col = 0;
        int a = 0,b = 0,c = 0;
        System.out.println(" Insert the column in which you want to put your draw :");
        Scanner in = new Scanner(System.in);
        col = in.nextInt();
        while(col < 0 || col > 8){
            System.out.println(" Put out of bound!!! You can only insert a number between 0 and 9. ");
            System.out.println(" col : ");
            in = new Scanner(System.in);
            col = in.nextInt();
        }
        System.out.println(" Insert the order ( 1 , 2 , 3 ) in which you want to put your draw : ");
        //TODO print your draw like a column of 3 item max.
        System.out.println(" First item ( from top ) : ");
        in = new Scanner(System.in);
        a = in.nextInt();
        //TODO print your draw like a column with the inserted order.
        in = new Scanner(System.in);
        b = in.nextInt();
        //TODO print your draw like a column with the inserted order.
        in = new Scanner(System.in);
        c = in.nextInt();
        //TODO print your draw like a column with the inserted order.
        return controller.setPut(a,b,c,col);
    }

    @Override
    public void printActions() {
        System.out.println("******************************************************************************************");
        System.out.println(" Actions: ");
        System.out.println(" (shutdown)         shutdown the APP ");
        System.out.println(" (chat)             chat with players ");
        System.out.println(" (draw)             draw a tiles from Board ");
        System.out.println(" (put)              put tiles in your Bookshelf ");
        System.out.println(" (common goal)      show your common goal  ");
        System.out.println(" (personal goal)    show your personal goal  ");
    }

    @Override
    public void updateBookshelf() {
        //TODO look in the controller for the draw array and the put order and update the bookshelf.
    }

    @Override
    public boolean reply() throws RemoteException {
        while(true){
            if(controller.reply_draw){
                controller.reply_draw = false;
                return controller.draw_valid;
            }
            if(controller.reply_put){
                controller.reply_put = false;
                return controller.put_valid;
            }
        }
    }

    public void printBookshelf(item[][] table) {
        System.out.println("    0 | 1 | 2 | 3 | 4 ");
        System.out.println("  ╔═══╦═══╦═══╦═══╦═══╗");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + " ║");
            for (int j = 0; j < 5; j++) {
                colorTile(table, i, j);
                System.out.print("║");
            }
            if (i < 5) System.out.println("\n  ╠═══╬═══╬═══╬═══╬═══╣");
        }
        System.out.println("\n  ╚═══╩═══╩═══╩═══╩═══╝");
    }

    private void colorTile(item[][] table, int i, int j) {
        switch (table[i][j]) {
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
            default:
                System.out.print(Color.BLACK_DEFAULT.escape() + "   " + Color.RESET);
        }
    }

}
