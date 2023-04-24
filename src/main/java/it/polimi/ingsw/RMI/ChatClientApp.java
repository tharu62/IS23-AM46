package it.polimi.ingsw.RMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientApp extends UnicastRemoteObject implements ChatClient{

    private ChatServer cs;

    protected ChatClientApp() throws RemoteException {
    }

    public static void main(String[] args )
    {
        System.out.println( "Hello from ChatClientApp!" );
        try {
            new ChatClientApp().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startClient() throws Exception {
        // Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(Settings.SERVER_NAME, Settings.PORT);

        // Looking up the registry for the remote object
        this.cs = (ChatServer) registry.lookup("ChatService");
        this.cs.login(this);
        inputLoop();
    }

    void inputLoop() throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        String message;
        while ( (message = br.readLine ()) != null) {
            cs.send(message);
        }
    }



    @Override
    public void receive(String message) throws RemoteException {
        System.out.println(message);
    }
}
