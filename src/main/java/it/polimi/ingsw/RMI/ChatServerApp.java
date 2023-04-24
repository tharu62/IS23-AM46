package it.polimi.ingsw.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerApp extends UnicastRemoteObject implements ChatServer{

    private final List<ChatClient> chatClients;
    public ChatServerApp() throws RemoteException {
        this.chatClients = new ArrayList<>();
    }
    public static void main(String[] args )
    {
        System.out.println( "Hello from ChatServerApp!" );
        try {
            new ChatServerApp().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws RemoteException {
        // Bind the remote object's stub in the registry
        //DO NOT CALL Registry registry = LocateRegistry.getRegistry();
        Registry registry = LocateRegistry.createRegistry(Settings.PORT);
        try {
            registry.bind("ChatService", this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");
    }

    @Override
    public void login(ChatClient cc) throws RemoteException {
        this.chatClients.add(cc);
    }

    @Override
    public void send(String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (ChatClient cc : chatClients) {
            cc.receive(message);
        }
    }
}
