package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.VIEW.GUI.GUI;
import javafx.stage.Stage;

public class guiHandler implements GameInterface {
    public GUI gui;

    public guiHandler(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void notifyInterface(String message) {
        gui.setNotification(message);
    }

    @Override
    public String getUsername(CONTROLLER controller) {
        while(gui.getUsername()){

        }
        return gui.Username.getText();
    }

    @Override
    public int getLobbySize(CONTROLLER controller) {
        while(gui.getLobbySize()){

        }
        return Integer.parseInt(gui.LobbySize.getText());
    }

    @Override
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        //TODO
    }

    @Override
    public void startInterface(Stage stage) throws Exception {
        gui.start(stage);
    }

}
