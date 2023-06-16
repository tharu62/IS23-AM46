package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.VIEW.CLI.CLI;

public class cliHandler implements GameInterface{
    public CLI cli;

    public cliHandler(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void notifyInterface(String message) {
        cli.cmd.notify(message);
    }

    @Override
    public String getUsername(CONTROLLER controller) {
        controller.username = cli.cmd.getUsername().toLowerCase();
        return controller.username;
    }

    @Override
    public int getLobbySize(CONTROLLER controller) {
        controller.LobbySize = cli.cmd.getLobbySize();
        return controller.LobbySize;
    }

    @Override
    public void startInterface() {
        cli.start();
    }
}
