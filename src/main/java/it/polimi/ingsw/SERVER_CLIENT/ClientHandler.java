package it.polimi.ingsw.SERVER_CLIENT;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private CONTROLLER controller= new CONTROLLER();
    private List<ClientHandler> clients= new ArrayList<>();
    public PrintWriter out;
    Command reply;
    Gson g;

    public ClientHandler(Socket socket, CONTROLLER controller, List<ClientHandler> clients) {
        this.socket = socket;
        this.controller = controller;
        this.clients = clients;
        clients.add(this);  /** da vedere se è valido per i thread **/
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            while (true) {
                String StrCommand = in.nextLine();
                Command ObjCommand = g.fromJson(StrCommand,Command.class);

                switch (ObjCommand.cmd){

                    case("LOGIN"):
                        reply.cmd="LOGIN";
                        reply.login= controller.setUsername(ObjCommand.login);
                        StrCommand = g.toJson(reply);
                        out.println(StrCommand);
                        if(!reply.login.accepted && reply.login.LobbyIsFull){
                            break;
                        }
                    case ("GAMEPLAY"):

                    case ("CHAT"):

                }

                if(!reply.login.accepted && reply.login.LobbyIsFull){
                    break;
                }
            }

            /** Socket is closed **/
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    synchronized private void broadcast(Command message){
        String temp= g.toJson(message);
        for(int i=0; i<clients.size(); i++){
            clients.get(i).out.println(temp);
        }
    }

}

