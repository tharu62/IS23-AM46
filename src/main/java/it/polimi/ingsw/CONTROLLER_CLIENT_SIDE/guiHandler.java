package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.GUI.GUI;

import java.util.List;

public class guiHandler implements GameInterface {
    public GUI gui;

    public guiHandler(GUI gui) {
        this.gui = gui;
    }


    /**
     * This method send the message to the GUI.
     *
     * @param message is the message received.
     */
    @Override
    public void notifyInterface(String message) {
        gui.Notify(message);
    }

    /**
     * This method wait until the player inserts his username in the GUI.
     *
     * @param controller is the controller of the GUI.
     * @return the username inserted by the player.
     */
    @Override
    public String getUsername(CONTROLLER controller) {
        while(gui.getUsernameNotSet());
        controller.username = GUI.loginData.username;
        return GUI.loginData.username;
    }

    /**
     * This method wait until the player inserts the LobbySize in the GUI.
     *
     * @param controller is the controller of the GUI.
     * @return the lobbySIze.
     */
    @Override
    public int getLobbySize(CONTROLLER controller) {
        while(gui.getLobbySizeNotSet());
        controller.LobbySize = GUI.loginData.lobbySize;
        return GUI.loginData.lobbySize;
    }

    /**
     * This method sends the chat message to the GUI if it's not a message sent by the player itself.
     *
     * @param controller is the controller of the GUI.
     * @param message is the message received.
     */
    @Override
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        if(!message.header[0].equals(controller.username)) {
            if(message.header[1].equals("everyone")) {
                gui.scrollChat(message);
            }
            if(message.header[1].equals(controller.username)){
                gui.scrollChat(message);
            }
        }
    }

    /**
     * This method starts the main method of the GUI.
     *
     * @param args is the argument in the main method of the APP.
     */
    @Override
    public void startInterface(String[] args){
        gui.main(args);
    }

    /**
     * This method sends the list of players to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param players is the list of players.
     */
    @Override
    public void setPlayers(CONTROLLER controller, List<String> players) {
        gui.setPlayers(players);
    }

    /**
     * This method sends the Board to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param grid is the Board.
     */
    @Override
    public void setBoard(CONTROLLER controller, item[][] grid) {
        gui.updateGrid(grid);
    }

    /**
     * This method sends the Common Goals to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param cardID is the list of unique ids of the Common Goals.
     * @param token is the list of the value of the token on top of the Common Goals.
     */
    @Override
    public void setCommonGoals(CONTROLLER controller, List<Integer> cardID, List<Integer> token) {
        gui.updateCommonGoals(cardID, token);
    }

    /**
     * This method sends the Personal Goal to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param cardID is the unique id of the Personal Goal Card.
     */
    @Override
    public void setPersonalGoal(CONTROLLER controller, int cardID) {
        gui.updatePersonalGoal(cardID);
    }

    /**
     * This method sends the Bookshelf to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param bookshelf is the bookshelf of the player.
     */
    @Override
    public void setBookshelf(CONTROLLER controller, item[][] bookshelf) {
        gui.updateBookshelf(bookshelf);
    }

    /**
     * This method sends the score to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param score is the score of the player.
     */
    @Override
    public void setScore(CONTROLLER controller, int score) {
        gui.setScore(score);
    }

    /**
     * This method sends the PlayerToPlay to the GUI.
     *
     * @param controller is the controller of the GUI.
     * @param ptp is the username of the pLayer to play.
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
     * @param controller is the controller of the GUI.
     * @param clientTCP is needed to differentiate between the TCP and RMI restart method.
     */
    @Override
    public void restartClient(CONTROLLER controller, ClientTCP clientTCP) {
        ClientTCP client = new ClientTCP(Settings.PORT_TCP, false);
        client.controller = controller;
        clientTCP.disconnected = true;
        GUI.cmd.replaceClient(client);
        clientTCP.start();
    }

    /**
     * This method allow the client to restart itself by giving the reference of the new clientRMI to the controller
     * and the cli. The method also starts the client.
     *
     * @param controller is the controller of the GUI.
     * @param clientRMI is the clientRMI to restart.
     * @throws Exception
     */
    @Override
    public void restartClient(CONTROLLER controller, ClientRMI clientRMI) throws Exception {
        clientRMI.controller = controller;
        GUI.cmd.replaceClient(clientRMI);
        clientRMI.start();
    }

}
