package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import javafx.stage.Stage;

import java.io.IOException;

public interface GameInterface {
    void notifyInterface(String message);
    String getUsername(CONTROLLER controller);
    int getLobbySize(CONTROLLER controller);
    void receiveChat(CONTROLLER controller, MESSAGE message);
    void startInterface(String[] args);

}
