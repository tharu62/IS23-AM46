package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.MESSAGE;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ClientHandlerRMI extends UnicastRemoteObject implements GameServer {

    CONTROLLER controller;
    public Map<GameClient, String> clientsRMI = new HashMap<>();
    final int PORT;
    public boolean disconnected;

    public ClientHandlerRMI(CONTROLLER controller, int port) throws RemoteException {
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
        if(controller.getLobbyIsFull()){
            gc.receiveLOG("LOBBY_IS_FULL");
        }else {
            if(controller.getCurrentPlayers() >= 1){
                controller.players++;
                put(gc,null);
                gc.receiveLOG("CONNECTED");
            }
            if(controller.getCurrentPlayers() == 0){
                controller.players++;
                put(gc, null);
                gc.receiveLOG("FIRST_TO_CONNECT");
            }
        }
    }

    @Override
    public boolean login(String username, GameClient client) throws RemoteException {
        if(clientsRMI.containsKey(client)){
            replace(client, username);
        }else{
            put(client, username);
        }
        if(!controller.setLogin(username)){
            replace(client, null);
            return false;
        }
        return true;
    }

    @Override
    public boolean loginFirst(String username, int LobbySize, GameClient client) throws RemoteException {
        if(clientsRMI.containsKey(client)){
            replace(client, username);
        }else{
            put(client, username);
        }
        if(!controller.setFirstLogin(username,LobbySize)){
            replace(client, null);
            return false;
        }
        return true;
    }

    @Override
    public boolean loginReconnect(String username, GameClient gc) throws RemoteException {
        put(gc, username);
        if(controller.setLoginReconnection(username)){
            put(gc, username);
            return true;
        }
        remove(gc);
        return false;
    }

    @Override
    public boolean askDraw(String username, int a, int b) throws RemoteException {
        return controller.setDraw( username , a , b );
    }

    @Override
    public boolean askPut(String username, int col, int a, int b, int c) throws RemoteException {
        return controller.setBookshelf( username, col , a , b , c );
    }

    @Override
    public void endTurn(String username) throws RemoteException {
        controller.setEndTurn(username);
    }

    @Override
    public void sendMessage(MESSAGE message) throws RemoteException {
        controller.setChat(message);
    }

    @Override
    public void pong() throws RemoteException {
        this.disconnected = false;
    }

    @Override
    public void ping(GameClient gc) throws RemoteException {
        gc.pong();
    }

    private void put(GameClient gc, String username){
        this.clientsRMI.put(gc, username);
    }

    private void remove(GameClient gc){
        this.clientsRMI.remove(gc);
    }

    private void replace(GameClient gc, String username){
        this.clientsRMI.replace(gc, username);
    }
}
