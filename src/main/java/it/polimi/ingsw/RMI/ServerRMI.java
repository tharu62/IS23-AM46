package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.TCP.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ServerRMI extends UnicastRemoteObject implements GameServer {

    CONTROLLER controller;
    public List<GameClient> clientsRMI;
    public List<ClientHandler> clientsTCP;
    final int PORT;

    public ServerRMI(CONTROLLER controller, int port, List<ClientHandler> clientsTCP) throws RemoteException {
        this.clientsRMI = new ArrayList<>();
        this.clientsTCP = new ArrayList<>();
        this.controller=controller;
        this.PORT = port;
        this.clientsTCP = clientsTCP;
    }
    public Runnable start() throws RemoteException {

        Registry registry = LocateRegistry.createRegistry(PORT);
        try {
            registry.bind("GameService", this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" Connection RMI ready ");

        // TUTTI I POSSIBILI INPUT DA SERVER:
        //clientsRMI.get(i).receiveMessage("");                             //BROADCAST
        //clientsRMI.get(i).receiveLOG("");                                 //BROADCAST
        //clientsRMI.get(i).receivePlayers(new List<String>);               //BROADCAST
        //clientsRMI.get(i).receiveBoard( new item[0][0]);                  //BROADCAST
        //clientsRMI.get(i).receiveCommonGoals(new List<COMMON_GOAL_CARD>); //BROADCAST
        //clientsRMI.get(i).receivePlayerToPlay("");                        //BROADCAST

        return null;
    }


    @Override
    public void connect(GameClient gc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        if(controller.LobbyIsFull){
            gc.receiveLOG("LOBBY_IS_FULL");
        }else {
            this.clientsRMI.add(gc);
            if(controller.connected_players >= 1){
                gc.receiveLOG("CONNECTED");
            }
            if(controller.connected_players == 0){
                gc.receiveLOG("FIRST_TO_CONNECT");
            }
        }
    }

    @Override
    public boolean login(String username) throws RemoteException {
        return controller.setLogin(username);
    }

    @Override
    public boolean loginFirst(String username, int LobbySize) throws RemoteException {
        return controller.setFirstLogin(username, LobbySize);
    }

    @Override
    public PERSONAL_GOAL_CARD sendPersonalGoal(String username) throws RemoteException {
        return controller.getPersonalGoalCards(username);
    }

    @Override
    public boolean askMyTurn(String username) throws RemoteException {
        return (controller.setTurn(username));
    }

    @Override
    public boolean askDraw(String username, int a, int b) throws RemoteException {
        return controller.setDraw( username , a , b );
    }

    @Override
    public boolean askPutItem(String username, int a, int b, int c, int col) throws RemoteException {
        return controller.setBookshelf( username, col , a , b , c );
    }

    @Override
    public int askCheckScore(String username) throws RemoteException {
        return controller.setScore(username);
    }

    @Override
    public boolean endTurn(String username) throws RemoteException {
        return controller.setEndTurn(username);
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (GameClient cc : clientsRMI) {
            cc.receiveMessage(message);
        }
    }

}
