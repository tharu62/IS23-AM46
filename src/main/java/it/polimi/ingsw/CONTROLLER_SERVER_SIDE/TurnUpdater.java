package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import com.google.gson.Gson;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.BROADCAST;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;
import it.polimi.ingsw.TCP.ClientHandlerTCP;
import it.polimi.ingsw.TCP.Command;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnUpdater {

    public void RMI(Map<GameClient, String> clientRMI, List<String> players, CONTROLLER controller, GAME game) throws RemoteException {

        List<Integer> tempId = new ArrayList<>();
        List<Integer> tempToken = new ArrayList<>();
        tempId.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        tempId.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        tempToken.add(controller.getCommonGoalCard(0).getToken_value());
        tempToken.add(controller.getCommonGoalCard(1).getToken_value());

        for (GameClient gc : clientRMI.keySet()) {
            try {

                gc.receiveBoard(controller.getBoard());
                gc.receivePlayers(players);
                gc.receiveCommonGoals(tempId, tempToken );
                gc.receivePersonalGoal(controller.getPersonalGoalCards(clientRMI.get(gc)));

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameClient gc : clientRMI.keySet()) {
            try {

                gc.receivePlayerToPlay(game.playerToPlay);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void RMI(Map<GameClient, String> clientRMI, CONTROLLER controller, GAME game) throws RemoteException {

        List<Integer> tempId = new ArrayList<>();
        List<Integer> tempToken = new ArrayList<>();
        tempId.add(controller.getCommonGoalCard(0).getCardLogic().getId());
        tempId.add(controller.getCommonGoalCard(1).getCardLogic().getId());
        tempToken.add(controller.getCommonGoalCard(0).getToken_value());
        tempToken.add(controller.getCommonGoalCard(1).getToken_value());

        if(game.master.round.last){
            for (GameClient gc : clientRMI.keySet()) {
                try {

                    gc.receiveLastRound();
                    gc.receiveBoard(controller.getBoard());
                    gc.receiveCommonGoals(tempId, tempToken );
                    gc.receiveBookshelf(controller.getBookshelf(clientRMI.get(gc)));
                    gc.receiveScore(controller.getScore(clientRMI.get(gc)));

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            for (GameClient gc : clientRMI.keySet()) {
                try {

                    gc.receiveBoard(controller.getBoard());
                    gc.receiveCommonGoals(tempId, tempToken );
                    gc.receiveBookshelf(controller.getBookshelf(clientRMI.get(gc)));
                    gc.receiveScore(controller.getScore(clientRMI.get(gc)));

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        for (GameClient gc : clientRMI.keySet()) {
            try {

                gc.receivePlayerToPlay(game.playerToPlay);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void TCP(List<ClientHandlerTCP> clientsTCP, List<String> players, CONTROLLER controller, GAME game){

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

        String reply_string;
        Gson g = new Gson();

        for(ClientHandlerTCP clientHandler : clientsTCP) {
            temp = new Command();
            temp.cmd = CMD.PERSONAL_GOAL;
            temp.gameplay = new GAMEPLAY();
            temp.gameplay.cardID = controller.getPersonalGoalCards(clientHandler.username);
            reply_string = g.toJson(temp);
            clientHandler.out.println(reply_string);
        }

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        clientsTCP.get(0).broadcast(temp);
    }

    public void TCP(List<ClientHandlerTCP> clientsTCP, CONTROLLER controller, GAME game){

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

        String reply_string;
        Gson g = new Gson();

        for(ClientHandlerTCP clientHandler : clientsTCP){
            temp = new Command();
            temp.cmd = CMD.BOOKSHELF;
            temp.gameplay = new GAMEPLAY();
            temp.gameplay.bookshelf = new item[6][5];
            temp.gameplay.bookshelf = controller.getBookshelf(clientHandler.username);
            reply_string = g.toJson(temp);
            clientHandler.out.println(reply_string);

            temp = new Command();
            temp.cmd = CMD.SCORE;
            temp.gameplay = new GAMEPLAY();
            temp.gameplay.pos = new ArrayList<>();
            temp.gameplay.pos.add(controller.getScore(clientHandler.username));
            reply_string = g.toJson(temp);
            clientHandler.out.println(reply_string);
        }

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
                List<Integer> tempId = new ArrayList<>();
                List<Integer> tempToken = new ArrayList<>();
                tempId.add(controller.getCommonGoalCard(0).getCardLogic().getId());
                tempId.add(controller.getCommonGoalCard(1).getCardLogic().getId());
                tempToken.add(controller.getCommonGoalCard(0).getToken_value());
                tempToken.add(controller.getCommonGoalCard(1).getToken_value());
                gc.receiveCommonGoals(tempId, tempToken );
                gc.receiveBookshelf(controller.getBookshelf(username));
                gc.receiveScore(controller.getScore(controller.clientsRMI.get(gc)));
                gc.receivePlayerToPlay(game.playerToPlay);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
    }

    public void reconnectTCP(ClientHandlerTCP client, List<String> players, CONTROLLER controller, GAME game){
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
        temp.gameplay.bookshelf = controller.getBookshelf(client.username);
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.SCORE;
        temp.gameplay = new GAMEPLAY();
        temp.gameplay.pos = new ArrayList<>();
        temp.gameplay.pos.add(controller.getScore(client.username));
        reply_string = g.toJson(temp);
        client.out.println(reply_string);

        temp = new Command();
        temp.cmd = CMD.PLAYER_TO_PLAY;
        temp.broadcast = new BROADCAST();
        temp.broadcast.ptp = game.playerToPlay;
        reply_string = g.toJson(temp);
        client.out.println(reply_string);
    }

    public void RMI_last(Map<GameClient, String> clientRMI, CONTROLLER controller, GAME game){
        for (GameClient gc : clientRMI.keySet()) {
            try {
                gc.receiveWinner(game.space.winner);
                gc.receiveScore(controller.getScore(clientRMI.get(gc)));

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void TCP_last(List<ClientHandlerTCP> clientsTCP, CONTROLLER controller, GAME game){

        String reply_string;
        Gson g = new Gson();
        Command temp = new Command();
        temp = new Command();

        for(ClientHandlerTCP clientHandler : clientsTCP){

            temp = new Command();
            temp.cmd = CMD.SCORE;
            temp.gameplay = new GAMEPLAY();
            temp.gameplay.pos = new ArrayList<>();
            temp.gameplay.pos.add(controller.getScore(clientHandler.username));
            reply_string = g.toJson(temp);
            clientHandler.out.println(reply_string);

            temp = new Command();
            temp = new Command();
            temp.cmd = CMD.WINNER;
            temp.username = game.space.winner;
            reply_string = g.toJson(temp);
            clientHandler.out.println(reply_string);

        }

    }
}
