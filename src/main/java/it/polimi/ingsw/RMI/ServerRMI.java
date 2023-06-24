package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.MESSAGE;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServerRMI extends UnicastRemoteObject implements GameServer {

    CONTROLLER controller;
    public Map<GameClient, String> clientsRMI = new HashMap<>();
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
    }

    @Override
    public void connect(GameClient gc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        if(controller.LobbyIsFull){
            gc.receiveLOG("LOBBY_IS_FULL");
        }else {
            if(controller.players >= 1){
                gc.receiveLOG("CONNECTED");
            }
            if(controller.players == 0){
                gc.receiveLOG("FIRST_TO_CONNECT");
            }
            this.clientsRMI.put(gc,null);
            controller.players++;
        }
    }

    @Override
    public boolean login(String username, GameClient client) throws RemoteException {
        clientsRMI.replace(client,username);
        return controller.setLogin(username);
    }

    @Override
    public boolean loginFirst(String username, int LobbySize, GameClient client) throws RemoteException {
        clientsRMI.replace(client,username);
        return controller.setFirstLogin(username,LobbySize);
    }

    @Override
    public boolean loginReconnect(String username, GameClient gc) throws RemoteException {
        if(controller.setLoginReconnection(username)){
            clientsRMI.put(gc, username);
            return true;
        }
        return false;
    }

    @Override
    public int sendPersonalGoal(String username) throws RemoteException {
        return controller.getPersonalGoalCards(username);
    }

    public void sendBookshelf(String username, GameClient gc) throws RemoteException{
        gc.receiveBookshelf(controller.getBookshelf(username));
    }

    @Override
    public void askLobbyReady(GameClient gc) throws RemoteException {
        if(controller.lobbyIsReady){
            gc.receiveLOG("CONNECTED");
        }
        else{
            gc.receiveLOG("LOBBY_IS_NOT_READY");
        }
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
