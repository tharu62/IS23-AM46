package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.VIEW.GUI.GUI;

public class guiHandler implements GameInterface{
    public GUI gui;

    public guiHandler(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void notifyInterface(String message) {

    }

    @Override
    public String getUsername(CONTROLLER controller) {
        return new String();
    }

    @Override
    public int getLobbySize(CONTROLLER controller) {
        return 0;
    }

    @Override
    public void startInterface() {

    }
}
