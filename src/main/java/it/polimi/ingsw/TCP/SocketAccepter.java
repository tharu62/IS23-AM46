package it.polimi.ingsw.TCP;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
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
    public List<ClientHandlerTCP> clientsTCP = new ArrayList<>();

    public SocketAccepter(CONTROLLER Controller, int port){
        this.controller = Controller;
        this.controller.clientsTCP = this.clientsTCP;
        this.PORT = port;
    }

    /**
     * This method waits for request of a new connection tcp via socket. If a request is issued the method makes
     * generates a new clientHandler in a new Thread to handle the new socket.
     */
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
            new ClientHandlerTCP(socket, controller, clientsTCP).start();
        }
        executor.shutdown();
    }

}
