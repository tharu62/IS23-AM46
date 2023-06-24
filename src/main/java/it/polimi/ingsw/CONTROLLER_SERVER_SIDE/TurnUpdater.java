package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import com.google.gson.Gson;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.BROADCAST;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.Command;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurnUpdater {

    public void RMI(Map<GameClient, String> clientRMI_HM, List<String> players, CONTROLLER controller, GAME game) throws RemoteException {
        for (GameClient gc : clientRMI_HM.keySet()) {
            try {

                gc.receiveBoard(controller.getBoard());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        for(GameClient gc : clientRMI_HM.keySet()){

            gc.receivePlayers(players);

        }

        for (GameClient gc : clientRMI_HM.keySet()) {
            try {

                gc.receiveCommonGoals(controller.getCommonGoalCard(0).getCardLogic().getId(), controller.getCommonGoalCard(0).getToken_value());
                gc.receiveCommonGoals(controller.getCommonGoalCard(1).getCardLogic().getId(), controller.getCommonGoalCard(1).getToken_value());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientRMI_HM.keySet()) {
            try {

                gc.receivePlayerToPlay(game.playerToPlay);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void RMI(Map<GameClient, String> clientRMI_HM, CONTROLLER controller, GAME game) throws RemoteException {

        if(game.master.round.last){
            for (GameClient gc : clientRMI_HM.keySet()) {
                try {

                    gc.receiveLastRound();

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        for (GameClient gc : clientRMI_HM.keySet()) {
            try {

                gc.receiveBoard(controller.getBoard());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientRMI_HM.keySet()) {
            try {

                gc.receiveCommonGoals(controller.getCommonGoalCard(0).getCardLogic().getId(), controller.getCommonGoalCard(0).getToken_value());
                gc.receiveCommonGoals(controller.getCommonGoalCard(1).getCardLogic().getId(), controller.getCommonGoalCard(1).getToken_value());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientRMI_HM.keySet()) {
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
        temp.broadcast.cardsValue = new ArrayList<>();
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(0).getToken_value());
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(1).getToken_value());
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
        temp.broadcast.cardsValue = new ArrayList<>();
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(0).getToken_value());
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(1).getToken_value());
        clientsTCP.get(0).broadcast(temp);

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        clientsTCP.get(0).broadcast(temp);
    }

    public void reconnectRMI(GameClient gc, List<String> players, CONTROLLER controller, GAME game, String username){
            try {
                gc.receiveBoard(controller.getBoard());
                gc.receivePlayers(players);
                gc.receiveCommonGoals(controller.getCommonGoalCard(0).getCardLogic().getId(), controller.getCommonGoalCard(0).getToken_value());
                gc.receiveCommonGoals(controller.getCommonGoalCard(1).getCardLogic().getId(), controller.getCommonGoalCard(1).getToken_value());
                gc.receiveBookshelf(controller.getBookshelf(username));
                gc.receivePlayerToPlay(game.playerToPlay);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
    }

    public void reconnectTCP(ClientHandler client, List<String> players, CONTROLLER controller, GAME game){
        String reply_string;
        Gson g = new Gson();

        Command temp = new Command();
        temp.cmd = CMD.BOARD;
        temp.broadcast = new BROADCAST();
        temp.broadcast.grid = new item[9][9];
        temp.broadcast.grid = controller.getBoard();
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.PLAYERS;
        temp.broadcast = new BROADCAST();
        temp.broadcast.players = new ArrayList<>();
        temp.broadcast.players = players;
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.COMMON_GOALS;
        temp.broadcast = new BROADCAST();
        temp.broadcast.cardsID = new ArrayList<>();
        temp.broadcast.cardsValue = new ArrayList<>();
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(0).getToken_value());
        temp.broadcast.cardsID.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        temp.broadcast.cardsValue.add(controller.getCommonGoalCard(1).getToken_value());
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.BOOKSHELF;
        temp.gameplay = new GAMEPLAY();
        temp.gameplay.bookshelf = new item[6][5];
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        reply_string = g.toJson(temp);
        client.out.println(reply_string);
    }

}
