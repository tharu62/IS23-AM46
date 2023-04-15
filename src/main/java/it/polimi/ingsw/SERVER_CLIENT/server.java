package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER.game.client.ActionSender;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    ActionSender controller;
    final int port;

    public server(ActionSender Controller, int port){
        this.controller= Controller;
        this.port= port;
    }

    public void start(){
        ExecutorService executor = Executors.newCachedThreadPool();  /** selects the usable threads **/
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
                executor.submit(new ClientHandler(socket, controller));     /** A new instance to handle the new Client is made **/
            } catch(IOException e) {
                break;
            }
            String userInput;

        }
        executor.shutdown();
    }


}
