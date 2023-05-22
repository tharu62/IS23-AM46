package it.polimi.ingsw.RMI;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;
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
    CONTROLLER controller;
    public boolean LoginOK;
    final int PORT;

    public ClientRMI(CONTROLLER controller, int port) throws RemoteException {
        this.controller = controller;
        this.PORT = port;
    }

    public void start() throws Exception {
        // Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, PORT);

        // Looking up the registry for the remote object
        gs = (GameServer) registry.lookup("GameService");
        gs.connect(this);

        //TODO
        // INPUT POSSIBILI DA VIEW
        //gs.connect( this );
        //gs.login(controller.username);
        //gs.loginFirst(controller.username, controller.LobbySize);
        //gs.sendPersonalGoal(controller.username);
        //gs.askMyTurn(controller.username);
        //gs.askDraw(controller.username, 1, 2);
        //gs.askPutItem(controller.username, 1, 2, 3, 4);
        //gs.askCheckScore(controller.username);
        //gs.endTurn(controller.username);
        //gs.sendMessage("");

    }


    @Override
    public void receiveMessage(MESSAGE message) throws RemoteException {
        if(message.header[1].equals(controller.username) || message.header[1].equals("everyone")){
            controller.notifyCLI(" NEW CHAT MESSAGE : ");
            controller.notifyCLI( message.header[0] + ":" + message.text);
        }
    }

    @Override
    public void receiveLOG(String message) throws RemoteException {
        if(message.equals("LOBBY_IS_FULL")){
            controller.LobbyIsFull = true;
            controller.notifyCLI("LOBBY_IS_FULL");
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
            controller.notifyCLI("CONNECTED");
            LoginOK = gs.login(controller.getUsername());
            while(!LoginOK){
                LoginOK = gs.login(controller.getUsername());
            }
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
    public void receiveCommonGoals(List<COMMON_GOAL_CARD> list) throws RemoteException {
        controller.setCommonGoals(list);
    }


    @Override
    public void receivePlayerToPlay(String ptp) throws RemoteException {
        controller.setPlayerToPlay(ptp);
    }

    @Override
    public void receiveWinner(String winner) {
        controller.notifyCLI(" THE GAME HAS ENDED, THE WINNER IS : " + winner);
    }

    @Override
    public void receivePersonalGoal(PERSONAL_GOAL_CARD p) throws RemoteException {
        controller.setPersonalGoal(p);
    }

    public static void sendMessage(MESSAGE message) throws RemoteException {
        gs.sendMessage(message);
    }
}
