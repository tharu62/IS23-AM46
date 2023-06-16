package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

public interface GameInterface {
    void notifyInterface(String message);
    String getUsername(CONTROLLER controller);
    int getLobbySize(CONTROLLER controller);
    void startInterface();

}
