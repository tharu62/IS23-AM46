package it.polimi.ingsw.RMI;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Client;
import it.polimi.ingsw.NETWORK.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements GameClient{
    public static GameServer gs;
    public CONTROLLER controller;
    public boolean LoginOK;
    final int PORT;

    public ClientRMI( int port ) throws RemoteException {
        this.PORT = port;
    }

    public void start() throws Exception {
        // Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, PORT);
        // Looking up the registry for the remote object
        gs = (GameServer) registry.lookup("GameService");
        gs.connect(this);
    }

    @Override
    public void receiveMessage(MESSAGE message) throws RemoteException {
        if(message.header[1].equals(controller.username) || message.header[1].equals("everyone")){
            controller.receiveChat(message);
        }
    }

    @Override
    public void receiveLOG(String message) throws RemoteException {
        if(message.equals("LOBBY_IS_FULL")){
            controller.notifyCLI("LOBBY_IS_FULL");
            controller.cli.cmd.notifyThread();
        }
        if(message.equals("FIRST_TO_CONNECT")){
            controller.notifyCLI("FIRST_TO_CONNECT");
            LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize());
            while(!LoginOK){
                controller.notifyCLI(" Username or lobby size not correct. Retry. ");
                LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize());
            }
        }
        if(message.equals("CONNECTED")){
            controller.setLobbyIsReady(true);
            controller.notifyCLI("CONNECTED");
            LoginOK = gs.login(controller.getUsername());
            while(!LoginOK){
                LoginOK = gs.login(controller.getUsername());
            }
        }
        if(message.equals("LOBBY_IS_NOT_READY")){
            controller.need_to_reconnect = true;
        }
    }


    @Override
    public void receivePlayers(List<String> players) throws RemoteException {
        controller.players = players;
    }


    @Override
    public void receiveBoard(item[][] grid) throws RemoteException {
        controller.notifyCLI("BOARD");
        controller.setBoard(grid);
    }


    @Override
    public void receiveCommonGoals(int cardID) throws RemoteException {
        controller.setCommonGoals(cardID);
    }


    @Override
    public void receivePlayerToPlay(String ptp) throws RemoteException {
        controller.setPlayerToPlay(ptp);
    }

    @Override
    public void receiveLastRound() throws RemoteException {
        controller.setLastRound();
    }

    @Override
    public void receiveWinner(String winner) {
        controller.notifyCLI(" THE GAME IS OVER, THE WINNER IS '"+ winner +"'");
    }

    @Override
    public void receivePersonalGoal(int p) throws RemoteException {
        controller.setPersonalGoal(p);
    }

    public static void sendMessage(MESSAGE message) throws RemoteException {
        gs.sendMessage(message);
    }

}
