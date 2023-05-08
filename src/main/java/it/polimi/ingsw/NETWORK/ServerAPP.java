package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.RMI.ServerRMI;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.ServerTCP;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServerAPP {

    public static void main(String[] args ) throws RemoteException {
        List<GameClient> clientsRMI = new ArrayList<>();
        List<ClientHandler> clientsTCP = new ArrayList<>();
        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);

        ServerTCP serverTCP = new ServerTCP(controller, Settings.PORT); // SERVER TCP //
        serverTCP.start( clientsRMI );

        ServerRMI serverRMI = new ServerRMI(controller, Settings.PORT); // SERVER RMI //
        serverRMI.startServerRMI( clientsTCP );

    }

}
