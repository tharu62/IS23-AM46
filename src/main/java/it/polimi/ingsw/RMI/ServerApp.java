package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerApp extends UnicastRemoteObject implements GameServer{

    private final List<GameClient> chatClients;
    public ServerApp() throws RemoteException {
        this.chatClients = new ArrayList<>();
    }
    public static void main(String[] args )
    {
        System.out.println( "Hello from ServerApp!" );
        try {
            new ServerApp().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws RemoteException {

        /**  Bind the remote object's stub in the registry
        /** DO NOT CALL Registry registry = LocateRegistry.getRegistry();
         **/
        Registry registry = LocateRegistry.createRegistry(Settings.PORT);
        try {
            registry.bind("GameService", this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");
    }

    @Override
    public void login(GameClient cc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        this.chatClients.add(cc);
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (GameClient cc : chatClients) {
            cc.receive(message);
        }
    }

    @Override
    public item[][] sendBoard() {
        item[][] grid = new item[1][1];
        grid[0][0]=item.EMPTY;
        return grid;
    }
}
