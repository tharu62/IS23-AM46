package it.polimi.ingsw.TCP;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.GameClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketAccepter extends Thread{
    CONTROLLER controller;
    final int PORT;
    public List<ClientHandler> clientsTCP = new ArrayList<>();
    public List<GameClient> clientsRMI;

    public SocketAccepter(CONTROLLER Controller, int port, List<GameClient> clientsRMI){
        this.controller= Controller;
        this.PORT= port;
        this.clientsRMI= clientsRMI;
    }

    @Override
    public void run( ){
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(" SocketAccepter ready ");
        while(!controller.LobbyIsFull) {

            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new ClientHandler(socket, controller, clientsTCP, clientsRMI).start();
        }
        executor.shutdown();
    }

}
