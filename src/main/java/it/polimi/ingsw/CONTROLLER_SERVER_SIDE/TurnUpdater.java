package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.BROADCAST;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.Command;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TurnUpdater {

    public void RMI(List<GameClient> clientsRMI, List<String> players, CONTROLLER controller, GAME game) throws RemoteException {
        for (GameClient gc : clientsRMI) {
            try {
                gc.receiveBoard(controller.getBoard());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        for(GameClient gc : clientsRMI){
            gc.receivePlayers(players);
        }

        for (GameClient gc : clientsRMI) {
            try {
                gc.receiveCommonGoals(controller.getCommonGoalCard(0).getCardLogic().getId());
                gc.receiveCommonGoals(controller.getCommonGoalCard(1).getCardLogic().getId());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientsRMI) {
            try {
                gc.receivePlayerToPlay(game.playerToPlay);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void RMI(List<GameClient> clientsRMI, CONTROLLER controller, GAME game) throws RemoteException {

        if(game.master.round.last){
            for (GameClient gc : clientsRMI) {
                try {
                    gc.receiveLastRound();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        for (GameClient gc : clientsRMI) {
            try {
                gc.receiveBoard(controller.getBoard());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientsRMI) {
            try {
                gc.receiveCommonGoals(controller.getCommonGoalCard(0).getCardLogic().getId());
                gc.receiveCommonGoals(controller.getCommonGoalCard(1).getCardLogic().getId());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientsRMI) {
            try {
                gc.receivePlayerToPlay(game.playerToPlay);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void TCP(List<ClientHandler> clientsTCP, List<String> players, CONTROLLER controller, GAME game){
        Command temp = new Command();
        temp.cmd = CMD.BOARD;
        temp.broadcast = new BROADCAST();
        temp.broadcast.grid = new item[9][9];
        temp.broadcast.grid = controller.getBoard();
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.PLAYERS;
        temp.broadcast = new BROADCAST();
        temp.broadcast.players = new ArrayList<>();
        temp.broadcast.players = players;
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.COMMON_GOALS;
        temp.broadcast = new BROADCAST();
        temp.broadcast.cardsID = new ArrayList<>();
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        clientsTCP.get(0).broadcast(temp);
    }

    public void TCP(List<ClientHandler> clientsTCP, CONTROLLER controller, GAME game){

        if(game.master.round.last){
            Command temp = new Command();
            temp.cmd = CMD.LAST_ROUND;
            clientsTCP.get(0).broadcast(temp);
        }

        Command temp = new Command();
        temp.cmd = CMD.BOARD;
        temp.broadcast = new BROADCAST();
        temp.broadcast.grid = new item[9][9];
        temp.broadcast.grid = controller.getBoard();
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.COMMON_GOALS;
        temp.broadcast = new BROADCAST();
        temp.broadcast.cardsID = new ArrayList<>();
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        clientsTCP.get(0).broadcast(temp);
    }
}
