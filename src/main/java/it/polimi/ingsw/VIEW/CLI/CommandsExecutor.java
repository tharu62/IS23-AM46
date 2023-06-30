package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.RMI;
import it.polimi.ingsw.VIEW.TCP;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CommandsExecutor implements CLI_commands {
    CONTROLLER controller;
    public CommunicationProtocol com;

    public CommandsExecutor(CONTROLLER controller, ClientRMI client) {
        this.controller = controller;
        this.com = new RMI(client);
    }

    public CommandsExecutor(CONTROLLER controller, ClientTCP client) {
        this.controller = controller;
        this.com = new TCP(client);
    }


    /**
     * This method ask the player for the username via Input Stream ( Scanner(System.in) ).
     *
     * @return username inserted by player input
     */
    @Override
    public String getUsername() {
        System.out.println( " Insert username:  ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This method ask the player for the LobbySize via Input Stream ( Scanner(System.in) ).
     *
     * @return lobbySIze inserted by player input.
     */
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

    /**
     * This method print the Board given as parameter.
     *
     * @param grid is the Board.
     */
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

    /**
     * This method prints the Personal Goal Card stored in the controller.
     */
    @Override
    public void printPersonalGoal() {
        int cardID = controller.PersonalGoalCardID;
        PrintPersonalGoals printPersonalGoals = new PrintPersonalGoals();
        System.out.println("    0 | 1 | 2 | 3 | 4 ");
        System.out.println("  ╔═══╦═══╦═══╦═══╦═══╗");
        printPersonalGoals.printGoal(cardID);
        System.out.println("  ╚═══╩═══╩═══╩═══╩═══╝");
    }

    /**
     * This method prints the Common Goal stored in the Controller.
     *
     * @param commonGoalCards is the list of the Common Goal Cards
     * @param commonGoalToken is the list of the Token values
     */
    @Override
    public void printCommonGoals(List<Integer> commonGoalCards, List<Integer> commonGoalToken) {
        PrintCommonGoals printCommonGoals = new PrintCommonGoals();
        int id1 = commonGoalCards.get(0);
        int id2 = commonGoalCards.get(1);
        int value1 = commonGoalToken.get(0);
        int value2 = commonGoalToken.get(1);
        System.out.print("\u001B[32m");
        printCommonGoals.printCommon(id1, value1);
        System.out.print(Color.RESET);
        System.out.print("\u001B[34m");
        printCommonGoals.printCommon(id2, value2);
        System.out.print(Color.RESET);
    }

    /**
     * This method allow the CLI to send chat messages to the client that send the messages to the Server.
     *
     * @return always true ( return not needed anymore )
     * @throws RemoteException
     */
    @Override
    public boolean sendChat() throws RemoteException {
        System.out.println(" Insert text: ");
        Scanner in = new Scanner(System.in);
        String text = in.nextLine();
        boolean inputValid = false;
        String receiver = null;
        while(!inputValid) {
            System.out.println(" Insert receiver from this choices: ");
            for (int i = 0; i < controller.players.size(); i++) {
                System.out.println(" PLAYER : " + controller.players.get(i));
            }
            System.out.println(" If you want to send a message to all players, insert string 'everyone' .");
            receiver = in.nextLine();
            if(equalsPlayerNames(receiver, controller.players) || receiver.equalsIgnoreCase("everyone")){
                inputValid = true;
            }
            else{
                System.out.println(" Input not valid! Retry.");
            }
        }
        com.sendChat(controller.username, text, receiver);
        return false;
    }


    /**
     * This method prints all the possible commands that the player can use to play the game in his turn on CLI.
     *
     */
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
        System.out.println(" (personal goal)    show your personal goal  ");
    }

    /**
     * This method prints all the possible commands that the player can use to play the game not in his turn on CLI.
     */
    @Override
    synchronized public void printActionsChat() {
        System.out.println("******************************************************************************************");
        System.out.println(" Actions: ");
        System.out.println(" (chat)         chat with players ");
        System.out.println(" (play)         if it's your turn you can stop the real time chat and start playing the game. ");
        System.out.println(" (bookshelf)    check your bookshelf. ");
    }

    /**
     * This method prints the chat messages in the chatBuffer stored in controller.
     */
    @Override
    synchronized public void printChatBuffer() {
        System.out.println(" CHAT MESSAGES THAT YOU DIDN'T SEE : ");
        for(int i = 0; i< controller.chatBuffer.size(); i++){
            if(controller.chatBuffer.get(i).header[1].equals("everyone")) {
                controller.notifyInterface(controller.chatBuffer.get(i).header[0] + " < public >:" + controller.chatBuffer.get(i).text);
            }
            if(controller.chatBuffer.get(i).header[1].equals(controller.username)){
                controller.notifyInterface(controller.chatBuffer.get(i).header[0] + " < private > : " + controller.chatBuffer.get(i).text);
            }
            controller.chatBuffer.remove(0);
        }
    }

    /**
     * This method prints the score stored in the controller.
     */
    @Override
    public void printScore() {
        System.out.println("YOUR SCORE (FROM COMMON GOALS TOKEN): " + controller.score);
    }

    /**
     * This method prints the bookshelf given as parameter.
     *
     * @param table is the bookshelf.
     */
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

    /**
     * This method ask the player for the input to draw an item from the board.
     * The methods check if the input is valid and signals the player if the input is not valid.
     * Once the draw input is done the method sends the draw information to the server.
     * The method does not receive any response from the server.
     *
     * @throws RemoteException
     */
    @Override
    public void askDraw() throws RemoteException {
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
        com.draw( controller.username, row , col, controller);
    }

    /**
     This method ask the player for the input to put an item in the bookshelf.
     * The methods check if the input is valid and signals the player if the input is not valid.
     * Once the draw input is done the method sends the draw information to the server.
     * The method does not receive any response from the server.
     *
     * @throws RemoteException
     */
    @Override
    public void putDraw() throws RemoteException {
        int col = -1;
        int a, b = -1, c = -1;
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
            System.out.println(" Second drawn item order of put ( from top ) : ");
            in = new Scanner(System.in);
            b = in.nextInt();
            while(b < 0 || b > 2 || b == a) {
                System.out.println(" Put order out of bound!! You can only insert a number between 0 and 2 : ");
                in = new Scanner(System.in);
                b = in.nextInt();
            }
            if(controller.draw.size() == 3) {
                System.out.println(" Third drawn item order of put ( from top ) : ");
                if ((a == 0 && b == 1) || (a == 1 && b == 0)) c = 2;
                if ((a == 0 && b == 2) || (a == 2 && b == 0)) c = 1;
                if ((a == 2 && b == 1) || (a == 1 && b == 2)) c = 0;
                System.out.println(c);
            }
        }
        else {
            a = 0;
        }
        com.put(controller.username, col, a, b, c, controller);
    }

    /**
     * This method resets all draw and put parameters, then it sends a request to end turn to the server.
     * This method does not receive any response from the server.
     *
     * @throws RemoteException
     */
    @Override
    public void endTurn() throws RemoteException {
        controller.draw.clear();
        controller.drawStatus = 0;
        controller.put = new int[4];
        controller.draw_end = false;
        controller.end_turn = false;
        com.endTurn(controller.username);
    }

    /**
     * This method is used to change the color of the content of a board or bookshelf based on the content itself.
     * ( in this case the item type define a specific color )
     *
     * @param table is the Board or Bookshelf
     * @param i row
     * @param j column
     */
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

    /**
     * This method checks if a reply for a draw request has been received.
     * If the reply arrives, the value of the reply is returned, else it loops forever.
     *
     * @return result of draw request.
     */
    @Override
    synchronized public boolean replyDraw() {
        while(true){
            if(controller.getReplyDraw()){
                controller.reply_draw = false;
                return controller.draw_valid;
            }
        }
    }

    /**
     * This method checks if a reply for a put request has been received.
     * If the reply arrives, the value of the reply is returned, else it loops forever.
     *
     * @return result of draw request.
     */
    @Override
    synchronized public boolean replyPut() {
        while(true){
            if(controller.getReplyPut()){
                controller.reply_put = false;
                return controller.put_valid;
            }
        }
    }

    /**
     * This method checks if a reply for end turn request has been sent.
     * If the reply arrives, the value of the reply is returned, else it loops forever.
     */
    @Override
    public void replyEndTurn() {
        while(true){
            if(controller.getGameDataReceived()){
                controller.gameDataReceived = false;
                return;
            }
        }
    }

    /**
     * This method allow the CLI to replace the reference to the client if the connection is lost and the reconnection
     * protocol has started.
     *
     * @param client is the client tcp reference.
     */
    @Override
    public void replaceClient(ClientTCP client) {
        com.replaceClient(client);
    }

    /**
     * This method allow the CLI to replace the reference to the client if the connection is lost and the reconnection
     * protocol has started.
     *
     * @param client is the client rmi reference
     */
    @Override
    public void replaceClient(ClientRMI client) {
        com.replaceClient(client);
    }

    /**
     * This method check of the given receiver string is contained in the given list of names.
     * @param receiver the username of the receiver.
     * @param names the usernames of players.
     * @return true if the receiver is contained in the names list, false otherwise.
     */
    private boolean equalsPlayerNames( String receiver, List<String> names){
        for (String name : names) {
            if (receiver.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
