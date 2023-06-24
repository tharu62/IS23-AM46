package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.GUI.CommandsExecutor;
import it.polimi.ingsw.VIEW.GUI.GUI;

public class guiHandler implements GameInterface {
    public GUI gui;

    public guiHandler(GUI gui, CONTROLLER controller, ClientRMI client) {
        this.gui = gui;
        GUI.controller = controller;
        GUI.cmd = new CommandsExecutor(controller, client, gui);
    }

    public guiHandler(GUI gui, CONTROLLER controller, ClientTCP client) {
        this.gui = gui;
        GUI.controller = controller;
        GUI.cmd = new CommandsExecutor(controller, client, gui);
    }

    @Override
    public void notifyInterface(String message) {
        if(GUI.gameplayData.gameSceneOpen){
            gui.setNotification(message);
        }else {
             if(GUI.loginData.loginSceneOpen) {
                gui.setGameNotification(message);
             }else {
                 if(GUI.appWindowOpen) {
                     gui.setLoginNotification(message);
                 }
            }
        }
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

}
