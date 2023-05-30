package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.Connection;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.Draw;
import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
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
        int counter = 0;
        System.out.println("******************************************************************************************");
        System.out.println(" WELCOME TO MY SHELFIE ONLINE GAME (CLI VERSION)");

        //TODO completare la fase iniziale di login automatico

        if(controller.connection == Connection.TCP) {
            while (!controller.getLobbyIsReady()) {
                if (controller.getMyTurn()) {
                    break;
                }
                if (controller.getNeedToReconnect()) {
                    System.out.println(" The first player has not chosen a lobby size yet, wait a little and retry to login. ");
                    System.out.println(" Trying to reconnect... ");
                    synchronized (this) {
                        try {
                            wait(2000);
                            controller.connect();
                            wait();
                        } catch (RemoteException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        if(controller.connection == Connection.RMI){
            while (!controller.getLobbyIsReady()) {
                if (controller.getNeedToReconnect()) {
                    if(counter == 0) {
                        System.out.println(" The first player has not chosen a lobby size yet, wait a bit to login. ");
                        System.out.println(" Trying to reconnect... ");
                        counter++;
                    }
                    try {
                        controller.connect();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

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

                if( StrCommand.equalsIgnoreCase("draw") && controller.draw_end){
                    System.out.println(" YOU HAVE DRAWN 3 ITEM! YOU CAN'T DRAW MORE. NOW YOU HAVE TO PUT YOUR DRAW IN YOUR BOOKSHELF! ");
                }
                if (!controller.draw_end && StrCommand.equalsIgnoreCase("draw")) {
                    try {
                        if(askDraw()){
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
                        if(putDraw()){
                            updateBookshelf();
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
                    printCommonGoals(controller.cards);
                    inputNotValid = false;
                }

                if(StrCommand.equalsIgnoreCase("personal goal")){
                    try {
                        printPersonalGoal(controller.getPersonalGoal());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    inputNotValid = false;
                }

                if(controller.end_turn && StrCommand.equalsIgnoreCase("end turn")){
                    try {
                        endTurn();
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

    /******************************************** INTERFACE_METHODS ***************************************************/
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
        int lobby_size = 0;
        try {
            System.out.println(" Insert Lobby size:  ");
            Scanner scanner = new Scanner(System.in);
            lobby_size = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter only numbers!");
        }
        return lobby_size;
    }

    @Override
    public void printBoard(item[][] grid) {
        System.out.println("   0  1  2  3  4  5  6  7  8");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + " ");
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
        System.out.print("\u001B[32m");
        printCommonGoals.printCommon(id1);
        System.out.print(Color.RESET);
        System.out.print("\u001B[34m");
        printCommonGoals.printCommon(id2);
        System.out.print(Color.RESET);
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
    public boolean askDraw() throws RemoteException, InterruptedException {
        int row = -1;
        int col = -1;
        Scanner in;
        do {
            in = new Scanner(System.in);
            try {
                System.out.println(" Insert row and column value of the item you want to draw from the Board :");
                System.out.print(" row : ");
                row = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter only numbers!");
            }
            if (row < -1 || row > 8) System.out.println(" Draw out of bound!!! You can insert only a number between 0 and 8. ");
        } while (row < 0 || row > 8);
        do {
            in = new Scanner(System.in);
            try {
                System.out.print(" col : ");
                col = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter only numbers!");
            }
            if (col < -1 || col > 8) System.out.println(" Draw out of bound!!! You can insert only a number between 0 and 8. ");
        } while (col < 0 || col > 8);
        if(controller.draw.size() <= controller.drawStatus){
            controller.draw.add(new Draw());
        }
        controller.draw.get(controller.drawStatus).coord[0] = row;
        controller.draw.get(controller.drawStatus).coord[1] = col;
        controller.draw.get(controller.drawStatus).item = controller.grid[row][col];
        return controller.setDraw(row,col);
    }

    @Override
    public boolean putDraw() throws RemoteException, InterruptedException {
        int col = -1;
        int a = -1,b = -1,c = -1;
        Scanner in;
        do {
            try {
                System.out.println(" Insert the column in which you want to put your draw : ");
                in = new Scanner(System.in);
                System.out.print(" col : ");
                col = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter only number!");
            }
            if (col < 0 || col > 4){
                System.out.println(" Put out of bound!!! You can only insert a number between 0 and 4. ");
            }
        } while (col < 0 || col > 4);
        //TODO print your draw like a column of 3 item max.

        if(controller.draw.size() > 1) {
            System.out.println(" Insert the order ( 0 , 1 , 2 ) in which you want to put your draw : ");
            System.out.println(" First drawn item order of put ( from top ) : ");
            in = new Scanner(System.in);
            a = in.nextInt();
            while(a < 0 || a > 2) {
                System.out.println(" Put order out of bound!! You can only insert a number between 0 and 2 : ");
                in = new Scanner(System.in);
                a = in.nextInt();
            }
            //TODO print your draw like a column with the inserted order.
            System.out.println(" Second drawn item order of put ( from top ) : ");
            in = new Scanner(System.in);
            b = in.nextInt();
            while(b < 0 || b > 2 || b == a) {
                System.out.println(" Put order out of bound!! You can only insert a number between 0 and 2 : ");
                in = new Scanner(System.in);
                b = in.nextInt();
            }
            if(controller.draw.size() == 3) {
                //TODO print your draw like a column with the inserted order.
                System.out.println(" Third drawn item order of put ( from top ) : ");
                if ((a == 0 && b == 1) || (a == 1 && b == 0)) c = 2;
                if ((a == 0 && b == 2) || (a == 2 && b == 0)) c = 1;
                if ((a == 2 && b == 1) || (a == 1 && b == 2)) c = 0;
                System.out.println(c);
                //TODO print your draw like a column with the inserted order.
            }
        }
        else {
            a = 0;
        }
        return controller.setPut(a,b,c,col);
    }

    @Override
    public void endTurn() throws RemoteException {
        controller.draw.clear();
        controller.drawStatus = 0;
        controller.put = new int[4];
        controller.endTurn();
    }

    @Override
    public void printActions() {
        System.out.println("******************************************************************************************");
        System.out.println(" Actions: ");
        System.out.println(" (shutdown)         shutdown the APP ");
        System.out.println(" (chat)             chat with players ");
        System.out.println(" (draw)             draw an item from the Board ");
        if (controller.drawStatus > 0){
            System.out.println(" (put)              put items in your Bookshelf ");
        }
        System.out.println(" (common goal)      show the common goals ");
        System.out.println(" (personal goal)    show your personal goal  ");
        System.out.println(" (end turn)         end your turn ");
    }

    @Override
    public void updateBookshelf() throws RemoteException {
        controller.setBookshelf();
    }

    @Override
    public boolean reply() {
        while(true){
            if(controller.reply_draw){
                controller.reply_draw = false;
                return controller.draw_valid;
            }
            if(controller.reply_put){
                controller.reply_put = false;
                System.out.println(" CHECK 5");
                return controller.put_valid;
            }
        }
    }

    @Override
    synchronized public void printBookshelf(item[][] table) {
        System.out.println(" ");
        System.out.println(" BOOKSHELF ");
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

    public int replyPersonal(){
        while(true){
            if(controller.reply_Personal){
                return controller.PersonalGoalCardID;
            }
        }
    }

    public void notifyThread(){
        synchronized (this){
            notifyAll();
        }
    }

}
