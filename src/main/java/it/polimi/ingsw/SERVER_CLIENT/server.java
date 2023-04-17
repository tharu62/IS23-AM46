package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    CONTROLLER controller;
    final int port;
    public List<ClientHandler> clients= new ArrayList<>();

    public server(CONTROLLER Controller, int port){
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
                clients.add(new ClientHandler(socket, controller, clients));
                executor.submit(clients.get(clients.size()-1));   /** si può fare? o devo istanziare il ClientHandler dentro l'executor?**/

            } catch(IOException e) {
                break;
            }
            String userInput;

        }
        executor.shutdown();
    }


}
