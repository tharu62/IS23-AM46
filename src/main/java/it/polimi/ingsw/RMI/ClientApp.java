package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientApp extends UnicastRemoteObject implements GameClient{

    private GameServer cs;


    protected ClientApp() throws RemoteException {
    }

    public static void main(String[] args )
    {
        System.out.println( "Hello from ClientApp!" );
        try {
            new ClientApp().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startClient() throws Exception {
        // Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, Settings.PORT);

        // Looking up the registry for the remote object
        this.cs = (GameServer) registry.lookup("GameService");
        this.cs.login(this);
        System.out.println( "Client is logged to Server!" );
        item[][] grid= cs.sendBoard();
        System.out.println ("server received: " + grid[0][0]);
        inputLoop();
    }

    void inputLoop() throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        String message;
        while ( (message = br.readLine ()) != null) {
            cs.sendMessage(message);
        }
    }



    @Override
    public void receive(String message) throws RemoteException {
        System.out.println(message);
    }


}
