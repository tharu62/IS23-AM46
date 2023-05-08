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

public class ServerTCP {
    CONTROLLER controller;
    final int port;
    public List<ClientHandler> clients= new ArrayList<>();

    public ServerTCP(CONTROLLER Controller, int port){
        this.controller= Controller;
        this.port= port;
    }

    public void start( List<GameClient> clientsRMI ){
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket, controller, clients, clientsRMI));
            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

}
