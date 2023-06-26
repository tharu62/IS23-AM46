package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.util.List;

public class cliHandler implements GameInterface{
    public CLI cli;

    public cliHandler(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void notifyInterface(String message) {
        cli.controller.notificationBuffer.add(message);
        cli.cmd.notifyCLI();
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

    @Override
    public void setPlayers(CONTROLLER controller, List<String> players) {
        controller.players = players;
    }


    @Override
    public void setBoard(CONTROLLER controller, item[][] grid) {
        controller.grid = grid;
    }

    @Override
    public void setCommonGoals(CONTROLLER controller, List<Integer> cardID, List<Integer> token) {
        controller.cards = cardID;
        controller.token_value = token;

    }

    @Override
    public void setPersonalGoal(CONTROLLER controller, int cardID) {
        controller.PersonalGoalCardID = cardID;
    }

    @Override
    public void setBookshelf(CONTROLLER controller, item[][] bookshelf) {
        controller.bookshelf = bookshelf;
    }

    @Override
    public void setScore(CONTROLLER controller, int score) {
        controller.score = score;
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
}
