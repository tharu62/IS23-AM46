package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.GUI.CommandsExecutor;
import it.polimi.ingsw.VIEW.GUI.GUI;
import javafx.stage.Stage;

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
        System.out.println(message);
        gui.setNotification(message);
    }

    @Override
    public String getUsername(CONTROLLER controller) {
        while(gui.getUsernameNotSet()){

        }
        return GUI.loginData.username;
    }

    @Override
    public int getLobbySize(CONTROLLER controller) {
        while(gui.getLobbySizeNotSet()){

        }
        return GUI.loginData.lobbySize;
    }

    @Override
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        //TODO
    }

    @Override
    public void startInterface(Stage stage){
        //
    }

}
