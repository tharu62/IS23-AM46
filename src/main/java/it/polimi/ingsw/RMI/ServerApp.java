package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.ServerTCP;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerApp extends UnicastRemoteObject implements GameServer{

    CONTROLLER controller;
    private final List<GameClient> clientsRMI;
    public List<ClientHandler> clientsTCP;

    public ServerApp(CONTROLLER controller) throws RemoteException {
        this.clientsRMI = new ArrayList<>();
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

        // broadcast of board, common goals, and first_player.
        while(true){
            if(controller.LobbyIsFull){
                for (GameClient gc : clientsRMI) {
                    gc.receiveBoard(controller.getBoard());
                }
                for (GameClient gc : clientsRMI) {
                    gc.receiveCommonGoals(controller.getCommonGoalCard());
                }
                for (GameClient gc : clientsRMI) {
                    gc.receivePlayerToPlay(controller.game.playerToPlay);
                }
                break;
            }
        }


        //TODO
        // TUTTI I POSSIBILI INPUT DA SERVER
        int i=0;

        clientsRMI.get(i).receiveMessage( new String() );                   //BROADCAST
        clientsRMI.get(i).receiveLOG( new String() );                       //BROADCAST
        //clientsRMI.get(i).receivePlayers(new List<String>);               //BROADCAST
        clientsRMI.get(i).receiveBoard( new item[0][0]);                    //BROADCAST
        //clientsRMI.get(i).receiveCommonGoals(new List<COMMON_GOAL_CARD>); //BROADCAST
        clientsRMI.get(i).receivePlayerToPlay( new String());               //BROADCAST

    }


    @Override
    public void connect(GameClient gc) throws RemoteException {
        System.out.println ("A new Client has appeared");
        if(controller.LobbyIsFull){
            gc.receiveLOG("LOBBY_IS_FULL");
        }else {
            this.clientsRMI.add(gc);
            if(controller.game.playerNumber==0){
                gc.receiveLOG("FIRST_TO_CONNECT");
            }
            if(controller.game.playerNumber>=1){
                gc.receiveLOG("CONNECTED");
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
