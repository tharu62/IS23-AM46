package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CLI_methods implements CLI_Interface{
    CONTROLLER controller;
    public COM com;

    public CLI_methods(CONTROLLER controller, ClientRMI client) {
        this.controller = controller;
        this.com = new RMI(client);
    }

    public CLI_methods(CONTROLLER controller, ClientTCP client) {
        this.controller = controller;
        this.com = new TCP(client);
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
        String text = in.nextLine();
        System.out.println(" Insert receiver: ");
        String receiver = in.nextLine();
        com.sendChat(controller.username, text , receiver);
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
        com.bookshelf( controller.cli , controller.username);
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

    @Override
    public void printBookshelf(item[][] table) {
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

    @Override
    public boolean askDraw() throws RemoteException {
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
        com.draw( controller.username, row , col, controller.draw_valid, controller.reply_draw);
        return true;
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
        com.put(controller.username, a, b, c, col,  controller.put_valid, controller.reply_put);
        return true;
    }

    @Override
    public void endTurn() throws RemoteException {
        controller.draw.clear();
        controller.drawStatus = 0;
        controller.put = new int[4];
        controller.draw_end = false;
        controller.put_end = false;
        controller.end_turn = false;
        com.endTurn(controller.myTurn, controller.username);
    }

    @Override
    public void colorTile(item[][] table, int i, int j) {
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

    @Override
    public int replyPersonal() {
        while (true) {
            if (controller.reply_Personal) {
                return controller.PersonalGoalCardID;
            }
        }
    }

    @Override
    public boolean replyDraw() throws InterruptedException {
        synchronized (this) {
            wait();
            return controller.draw_valid;
        }
    }

    @Override
    public boolean replyPut() throws InterruptedException {
        synchronized (this) {
            wait();
            return controller.put_valid;
        }
    }
    @Override
    public void notifyThread() {
        synchronized (this) {
            notifyAll();
        }
    }
}
