package it.polimi.ingsw.RMI;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements GameClient{
    public static GameServer gs;
    public CONTROLLER controller;
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
    public void ping() throws RemoteException {

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
            controller.notifyInterface("LOBBY_IS_FULL");
        }
        if(message.equals("FIRST_TO_CONNECT")){
            controller.notifyInterface("FIRST_TO_CONNECT");
            controller.LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize());
            while(!controller.LoginOK){
                controller.notifyInterface(" Username or lobby size not correct. Retry. ");
                controller.LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize());
            }
        }
        if(message.equals("CONNECTED")){
            controller.setLobbyIsReady(true);
            controller.notifyInterface("CONNECTED");
            controller.LoginOK = gs.login(controller.getUsername());
            while(!controller.LoginOK){
                controller.LoginOK = gs.login(controller.getUsername());
            }
        }
        if(message.equals("USER_DISCONNECTED")){
            controller.notifyInterface(" The player '" + message + "' has disconnected, the match is over by default.");
            controller.notifyInterface(" AUTO-SHUTDOWN ...");
            synchronized (this){
                try {
                    this.wait(3000);
                }catch (InterruptedException e){
                    System.exit(0);
                }
            }
            System.exit(0);
        }
    }

    @Override
    public void receivePlayers(List<String> players) throws RemoteException {
        controller.players = players;
    }

    @Override
    public void receiveBoard(item[][] grid) throws RemoteException {
        controller.setBoard(grid);
    }

    @Override
    public void receiveCommonGoals(int cardID, int token) throws RemoteException {
        controller.setCommonGoals(cardID, token);
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
        controller.notifyInterface(" THE GAME IS OVER, THE WINNER IS '" + winner + "'");
    }

    @Override
    public void receivePersonalGoal(int p) throws RemoteException {
        controller.setPersonalGoal(p);
    }

    @Override
    public void receiveBookshelf(item[][] bookshelf) throws RemoteException {
        controller.bookshelf = bookshelf;
        controller.bookshelf_received = true;
    }

    public static void sendMessage(MESSAGE message) throws RemoteException {
        gs.sendMessage(message);
    }

}

