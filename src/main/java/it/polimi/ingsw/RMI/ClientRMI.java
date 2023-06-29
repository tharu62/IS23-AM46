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

public class ClientRMI extends UnicastRemoteObject implements GameClient {
    public static GameServer gs;
    public CONTROLLER controller;
    final int PORT;
    public boolean crashed;
    public boolean disconnected = false;


    public ClientRMI( int port, boolean crashed ) throws RemoteException {
        this.PORT = port;
        this.crashed = crashed;
    }

    public void start() throws Exception {

        // Getting the registry
        Registry registry;
        // Looking up the registry for the remote object
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, PORT);
        System.setProperty("java.rmi.server.hostname", Settings.SERVER_NAME);
        gs = (GameServer) registry.lookup("GameService");
        gs.connect(this);

    }

    @Override
    public void ping() throws RemoteException {
        gs.pong();
    }

    @Override
    public void pong() throws RemoteException {
        this.disconnected = false;
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
            if(crashed){
                controller.LoginOK = gs.loginReconnect(controller.getUsername(), this);
                while(!controller.LoginOK){
                    controller.LoginOK = gs.loginReconnect(controller.getUsername(), this);
                }
            }else{
                if(disconnected){
                    if(!gs.loginReconnect(controller.username, this)){
                        controller.notifyInterface("LOBBY_IS_FULL");
                        System.exit(0);
                    }
                }
                controller.notifyInterface("LOBBY_IS_FULL");
                System.exit(0);
            }
        }
        if(message.equals("FIRST_TO_CONNECT")){
            controller.notConnected = false;
            controller.firstToConnect = true;
            controller.notifyInterface("FIRST_TO_CONNECT");
            controller.LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize(), this);
            while(!controller.LoginOK){
                controller.notifyInterface(" Username or lobby size not correct. Retry. ");
                controller.LoginOK = gs.loginFirst(controller.getUsername(), controller.getLobbySize(), this);
            }
            controller.notifyInterface(" LOGIN_OK ");
        }
        if(message.equals("CONNECTED")){
            controller.notConnected = false;
            controller.notifyInterface("CONNECTED");
            controller.LoginOK = gs.login(controller.getUsername(), this);
            while(!controller.LoginOK) {
                controller.LoginOK = gs.login(controller.getUsername(), this);
            }
            controller.notifyInterface(" LOGIN_OK ");
        }
    }

    @Override
    public void receiveDisconnectedPlayer(String username) throws RemoteException {
        controller.notifyInterface(" The player <" + username + "> has disconnected");
    }

    @Override
    public void receivePlayers(List<String> players) throws RemoteException {
        controller.setPlayers(players);
    }

    @Override
    public void receiveBoard(item[][] grid) throws RemoteException {
        controller.setBoard(grid);
    }

    @Override
    public void receiveBookshelf(item[][] bookshelf) throws RemoteException {
        controller.setBookshelf(bookshelf);
    }

    @Override
    public void receiveCommonGoals(List<Integer> cardID, List<Integer> token) throws RemoteException {
        controller.setCommonGoals(cardID, token);
    }

    @Override
    public void receiveScore(int score) throws RemoteException {
        controller.setScore(score);
    }

    @Override
    public void receivePersonalGoal(int p) throws RemoteException {
        controller.setPersonalGoal(p);
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
        controller.gameIsOver = true;
        controller.notifyInterface(" THE GAME IS OVER, THE WINNER IS '" + winner + "'");
    }

    public static void sendMessage(MESSAGE message) throws RemoteException {
        gs.sendMessage(message);
    }

}

