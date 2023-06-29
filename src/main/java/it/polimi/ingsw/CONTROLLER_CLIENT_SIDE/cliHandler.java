package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.util.List;

public class cliHandler implements GameInterface{
    public CLI cli;

    public cliHandler(CLI cli) {
        this.cli = cli;
    }


    /**
     * This method prints on the CLI the message given as parameter.
     *
     * @param message is the String to print on the CLI.
     */
    @Override
    public void notifyInterface(String message) {
        System.out.println("******************************************************************************************");
        System.out.println(message);
        System.out.println("******************************************************************************************");
    }

    /**
     * This method request the username from player input of the CLI and saves the given String in the controller.
     *
     * @param controller is the controller of the CLI.
     * @return the username given by the player.
     */
    @Override
    public String getUsername(CONTROLLER controller) {
        controller.username = cli.cmd.getUsername().toLowerCase();
        return controller.username;
    }

    /**
     * This method request the LobbySize from player input of the CLI and saves the given int in the controller.
     *
     * @param controller is the controller of the CLI.
     * @return the lobbySize given by the player.
     */
    @Override
    public int getLobbySize(CONTROLLER controller) {
        controller.LobbySize = cli.cmd.getLobbySize();
        return controller.LobbySize;
    }

    /**
     * This method prints the chat message in a specific format if it's not the player's turn, otherwise it saves the
     * chat message in a buffer in controller.
     *
     * @param controller is the controller of the CLI.
     * @param message is the message received.
     */
    @Override
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        if(controller.myTurn) {
            if(!message.header[0].equals(controller.username)){
                controller.chatBuffer.add(message);
            }
        }else{
            if(message.header[1].equals("everyone")) {
                if(!message.header[0].equals(controller.username)){
                    controller.Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    controller.Interface.notifyInterface(message.header[0] + " <public> : " + message.text);
                }
            }
            if(message.header[1].equals(controller.username)){
                if(!message.header[0].equals(controller.username)){
                    controller.Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    controller.Interface.notifyInterface(message.header[0] + " <private> : " + message.text);
                }
            }
        }
    }

    /**
     * This method starts the CLI in a new Thread.
     *
     * @param args is the argument given in the main method of the App, it is not needed for the GUI.
     */
    @Override
    public void startInterface(String[] args) {
        cli.start();
    }

    /**
     * This method stores the name of the other player in the controller.
     *
     * @param controller is the controller of the CLI.
     * @param players is a list of usernames.
     */
    @Override
    public void setPlayers(CONTROLLER controller, List<String> players) {
        controller.players = players;
    }


    /**
     * This method stores the Board given in the controller.
     *
     * @param controller is the controller of the CLI.
     * @param grid is the Board to store.
     */
    @Override
    public void setBoard(CONTROLLER controller, item[][] grid) {
        controller.grid = grid;
    }

    /**
     * This method stores the Common Goals in the controller.
     * The logic of the card and the token value are store in different attributes.
     *
     * @param controller is the controller of the CLI.
     * @param cardID is the unique ID of the Common Goal Card that gives information about its logic.
     * @param token is the value of the first token on top of the Common Goal Card.
     */
    @Override
    public void setCommonGoals(CONTROLLER controller, List<Integer> cardID, List<Integer> token) {
        controller.cards = cardID;
        controller.token_value = token;

    }

    /**
     * This method stores the Personal Goal Card in the controller.
     *
     * @param controller is the controller of the CLI.
     * @param cardID is the unique ID of the Personal Goal Card that gives information about its logic.
     */
    @Override
    public void setPersonalGoal(CONTROLLER controller, int cardID) {
        controller.PersonalGoalCardID = cardID;
    }

    /**
     * This method stores the Bookshelf given in the controller.
     *
     * @param controller is the controller of the CLI.
     * @param bookshelf is the bookshelf of the player.
     */
    @Override
    public void setBookshelf(CONTROLLER controller, item[][] bookshelf) {
        controller.bookshelf = bookshelf;
    }

    /**
     * This method stores the score of the player in the controller.
     *
     * @param controller is the controller of the CLI.
     * @param score is the score of the player.
     */
    @Override
    public void setScore(CONTROLLER controller, int score) {
        controller.score = score;
    }

    /**
     * This method saves the new PlayerToPlay in the controller and notifies the CLI ( on the spot ) if it's his turn
     * or not. The method also indicates that all other information of the game have been received.
     *
     * @param controller is the controller of the CLI.
     * @param ptp is the username of the player to play.
     */
    @Override
    public void setPlayerToPlay(CONTROLLER controller, String ptp) {
        if( controller.username.toLowerCase().equals(ptp) ){
            controller.notifyInterface("                                 IT IS YOUR TURN                                          ");
            controller.myTurn = true;
        }
        else{
            controller.notifyInterface("                                IT IS NOT YOUR TURN                                       ");
            controller.myTurn = false;
        }
        controller.gameDataReceived = true;
    }

    /**
     * This method allow the client to restart itself by making a new clientTCP.
     *
     * @param controller is the controller of the CLI.
     * @param clientTCP is needed to differentiate between the TCP and RMI restart method.
     */
    @Override
    public void restartClient(CONTROLLER controller, ClientTCP clientTCP) {
        clientTCP = new ClientTCP(Settings.PORT_TCP, false);
        clientTCP.controller = controller;
        clientTCP.disconnected = true;
        cli.cmd.replaceClient(clientTCP);
        clientTCP.start();
    }

    /**
     * This method allow the client to restart itself by giving the reference of the new clientRMI to the controller
     * and the cli. The method also starts the client.
     *
     * @param controller is the controller of the CLI.
     * @param clientRMI is the clientRMI to restart.
     */
    @Override
    public void restartClient(CONTROLLER controller, ClientRMI clientRMI) {
        clientRMI.controller = controller;
        cli.cmd.replaceClient(clientRMI);
        try {
            clientRMI.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
