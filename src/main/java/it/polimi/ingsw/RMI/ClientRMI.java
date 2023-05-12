package it.polimi.ingsw.RMI;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements GameClient{

    private GameServer gs;
    CONTROLLER controller;
    public String message;
    public boolean LoginOK;

    protected ClientRMI() throws RemoteException {
    }

    public static void main(String[] args )
    {
        System.out.println( "Hello from ClientApp!" );
        try {
            new ClientRMI().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startClient() throws Exception {
        // Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, Settings.PORT);

        // Looking up the registry for the remote object
        this.gs = (GameServer) registry.lookup("GameService");
        this.gs.connect(this);
        System.out.println( "Client is logged to Server!" );

        //TODO
        // INPUT POSSIBILI DA VIEW
        gs.connect( this );
        gs.login(controller.username);
        gs.loginFirst(controller.username, controller.LobbySize);
        gs.sendPersonalGoal(controller.username);
        gs.askMyTurn(controller.username);
        gs.askDraw(controller.username, 1, 2);
        gs.askPutItem(controller.username, 1, 2, 3, 4);
        gs.askCheckScore(controller.username);
        gs.endTurn(controller.username);
        gs.sendMessage("");


        inputLoop();
    }

    void inputLoop() throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        while( (this.message = br.readLine()) != null) {
            //TODO
            break;
        }
    }


    @Override
    public void receiveMessage(String message) throws RemoteException {

    }

    @Override
    public void receiveLOG(String message) throws RemoteException {
        if(message.equals("LOBBY_IS_FULL")){

            controller.LobbyIsFull = true;
            /**
             * update view by the controller and tells the player there is no match available.
             */
        }
        if(message.equals("FIRST_TO_CONNECT")){
            LoginOK = gs.loginFirst(controller.username, controller.getLobbySize());
            while(!LoginOK){
                LoginOK = gs.loginFirst(controller.username, controller.getLobbySize());
            }
            /**
             * update view by the controller and ask the player to choose the player number and the username.
             */
        }
        if(message.equals("CONNECTED")){
            gs.login(controller.getUsername());
            while(!LoginOK){
                LoginOK=gs.login(controller.getUsername());
            }
            /**
             * update view by the controller and ask the player to choose the username.
             */
        }
        System.out.println(message);
    }


    @Override
    public void receivePlayers(List<String> players) throws RemoteException {
        /** controller.players = players; **/
    }


    @Override
    public void receiveBoard(item[][] grid) throws RemoteException {
        controller.grid=grid;
    }


    @Override
    public void receiveCommonGoals(List<COMMON_GOAL_CARD> list) throws RemoteException {
        controller.setCommonGoals(list);
    }


    @Override
    public void receivePlayerToPlay(String username) throws RemoteException {
        if(controller.username.equals(username)){
            controller.myTurn = true;
        }
    }

}
