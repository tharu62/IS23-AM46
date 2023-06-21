package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.VIEW.CLI.CLI;
import javafx.stage.Stage;

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
    public void receiveChat(CONTROLLER controller, MESSAGE message) {
        if(controller.myTurn) {
            if(!message.header[0].equals(controller.username)){
                controller.chatBuffer.add(message);
            }
        }else{
            if(message.header[1].equals("everyone")) {
                if(!message.header[0].equals(controller.username)){
                    controller.Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    controller.Interface.notifyInterface(message.header[0] + " < public >:" + message.text);
                }
            }
            if(message.header[1].equals(controller.username)){
                if(!message.header[0].equals(controller.username)){
                    controller.Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    controller.Interface.notifyInterface(message.header[0] + " < private > : " + message.text);
                }
            }
        }
    }

    @Override
    public void startInterface(String[] args) {
        cli.start();
    }
}
