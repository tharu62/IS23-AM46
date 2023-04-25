package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerApp extends UnicastRemoteObject implements GameServer{

    CONTROLLER controller;
    private final List<GameClient> Clients;
    public ServerApp() throws RemoteException {
        this.Clients = new ArrayList<>();
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

        /**
         * broadcast of board, common goals, and first_player.
         */
        do {
            if(controller.LobbyIsFull){
                for (GameClient gc : Clients) {
                    gc.receiveBoard(controller.getBoard());
                }
                for (GameClient gc : Clients) {
                    gc.receiveCommonGoals(controller.getCommonGoalCard());
                }
                for (GameClient gc : Clients) {
                    gc.receivePersonalGoals();
                }

                for (GameClient gc : Clients) {
                    gc.receivePlayerToPlay(controller.game.playerToPlay);
                }
                break;
            }
        }while(!controller.LobbyIsFull);



    }

    @Override
    public void connect(GameClient gc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        if(controller.LobbyIsFull){
            gc.receive("Lobby_is_full");
        }else {
            this.Clients.add(gc);
            if(controller.game.playerNumber==0){
                gc.receive("FIRST_TO_CONNECT");
            }
            if(controller.game.playerNumber>=1){
                gc.receive("CONNECTED");
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
    public void startTurn(String username) throws RemoteException {

    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (GameClient cc : Clients) {
            cc.receive(message);
        }
    }




}
