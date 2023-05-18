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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerAPP {

    public void run() throws RemoteException, InterruptedException {
        List<GameClient> clientsRMI = new ArrayList<>();
        List<ClientHandler> clientsTCP = new ArrayList<>();
        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);

        ExecutorService executor = Executors.newCachedThreadPool();

        ServerTCP serverTCP= new ServerTCP(controller, Settings.PORT); // SERVER TCP //
        executor.submit( serverTCP.start( clientsRMI ));

        ServerRMI serverRMI= new ServerRMI(controller, Settings.PORT); // SERVER RMI //
        executor.submit( serverRMI.startServerRMI( clientsTCP ));

    }

}
