package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.GUI.GUI;

import java.rmi.RemoteException;
import java.util.List;

public class guiHandler implements GameInterface {
    public GUI gui;

    public guiHandler(GUI gui) {
        this.gui = gui;
    }

    /******************************************************************************************************************/

    @Override
    public void notifyInterface(String message) {
        gui.Notify(message);
    }

    @Override
    public String getUsername(CONTROLLER controller) {
        while(gui.getUsernameNotSet());
        controller.username = GUI.loginData.username;
        return GUI.loginData.username;
    }

    @Override
    public int getLobbySize(CONTROLLER controller) {
        while(gui.getLobbySizeNotSet());
        controller.LobbySize = GUI.loginData.lobbySize;
        return GUI.loginData.lobbySize;
    }

    @Override
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        if(!message.header[0].equals(controller.username)) {
            if(message.header[1].equals("everyone")) {
                gui.scrollChat(message, false);
            }
            if(message.header[1].equals(controller.username)){
                gui.scrollChat(message, true);
            }
        }
    }

    @Override
    public void startInterface(String[] args){
        gui.main(args);
    }

    @Override
    public void setPlayers(CONTROLLER controller, List<String> players) {
        gui.setPlayers(players);
    }

    @Override
    public void setBoard(CONTROLLER controller, item[][] grid) {
        gui.updateGrid(grid);
    }

    @Override
    public void setCommonGoals(CONTROLLER controller, List<Integer> cardID, List<Integer> token) {
        gui.updateCommonGoals(cardID, token);
    }

    @Override
    public void setPersonalGoal(CONTROLLER controller, int cardID) {
        gui.updatePersonalGoal(cardID);
    }

    @Override
    public void setBookshelf(CONTROLLER controller, item[][] bookshelf) {
        gui.updateBookshelf(bookshelf);
    }

    @Override
    public void setScore(CONTROLLER controller, int score) {
        gui.setScore(score);
    }

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

    @Override
    public void restartClient(CONTROLLER controller, ClientTCP clientTCP) {
        ClientTCP client = new ClientTCP(Settings.PORT_TCP, false);
        client.controller = controller;
        GUI.cmd.replaceClient(client);
    }

    @Override
    public void restartClient(CONTROLLER controller, ClientRMI clientRMI) {
        try {
            ClientRMI client = new ClientRMI(Settings.PORT_RMI, false);
            client.controller = controller;
            GUI.cmd.replaceClient(client);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

}
