package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.SERVER_CLIENT.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerApp extends UnicastRemoteObject implements GameServer{

    CONTROLLER controller;
    private final List<GameClient> Clients;
    public ServerApp(CONTROLLER controller) throws RemoteException {
        this.Clients = new ArrayList<>();
        this.controller=controller;
    }
    public static void main(String[] args )
    {
        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);
        System.out.println( "Hello from ServerApp!" );
        ServerTCP serverTCP = new ServerTCP(controller, Settings.PORT);  /** SERVER TCP **/
        try {
            new ServerApp(controller).startServerRMI();               /** SERVER RMI **/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServerRMI() throws RemoteException {

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
    public boolean askMyTurn(String username) throws RemoteException {
        /** return (controller.game.PlayerToPlay == username ); **/
        return true;
    }

    @Override
    public boolean askDraw(String username, int a, int b) throws RemoteException {
        /** return controller.draw( username , a , b ); **/
        return false;
    }

    @Override
    public boolean askPutItem(String username, int a, int b, int c, int col) throws RemoteException {
        /** return controller.putItem( username, a , b , c , col ); **/
        return false;
    }

    @Override
    public int askCheckScore(String username) throws RemoteException {
        /** return contorller.checkScore( username ); **/
        return 0;
    }

    @Override
    public boolean endTurn(String username) throws RemoteException {
        /** return controller.endTurn( username ); **/
        return false;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (GameClient cc : Clients) {
            cc.receive(message);
        }
    }

}