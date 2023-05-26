package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI extends UnicastRemoteObject implements GameServer {

    CONTROLLER controller;
    public List<GameClient> clientsRMI= new ArrayList<>();;
    final int PORT;

    public ServerRMI(CONTROLLER controller, int port) throws RemoteException {
        this.controller = controller;
        this.controller.clientsRMI = this.clientsRMI;
        this.PORT = port;
    }
    public void start() throws RemoteException {

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

    }


    @Override
    public void connect(GameClient gc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        if(controller.LobbyIsFull){
            gc.receiveLOG("LOBBY_IS_FULL");
        }else {
            this.clientsRMI.add(gc);
            if(controller.getCurrentLobbySize() >= 1){
                gc.receiveLOG("CONNECTED");
            }
            if(controller.getCurrentLobbySize() == 0){
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
        return controller.setFirstLogin(username,LobbySize);
    }

    @Override
    public int sendPersonalGoal(String username) throws RemoteException {
        return controller.getPersonalGoalCards(username);
    }

    public item[][] sendBookshelf(String username) throws RemoteException{
        return controller.getBookshelf(username);
    }

    @Override
    public boolean askMyTurn(String username) throws RemoteException {
        return controller.setTurn(username);
    }

    @Override
    public boolean askDraw(String username, int a, int b) throws RemoteException {
        return controller.setDraw( username , a , b );
    }

    @Override
    public boolean askPutItem(String username,  int col, int a, int b, int c) throws RemoteException {
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
    public void sendMessage(MESSAGE message) throws RemoteException {
        controller.setChat(message);
    }

}
