package it.polimi.ingsw.SERVER_CLIENT;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.SERVER_CLIENT.COMANDS.LOGIN;

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
        clients.add(this);
    }
    public void run() {

        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());


            while (true) {
                String StrCommand = in.nextLine();
                Command ObjCommand = g.fromJson(StrCommand,Command.class);

                /** this method contains the logic to check and reply for all type of messages sent form the client **/
                CommandSwitcher(ObjCommand);

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
            if(clients.get(i)!=this) {
                clients.get(i).out.println(temp);
            }
        }
    }

    synchronized private void CommandSwitcher(Command ObjCommand){
        switch (ObjCommand.cmd){

            case("LOGIN"):
                LOGIN temp= new LOGIN();
                reply.cmd="LOGIN";
                controller.setUsername(ObjCommand.login);
                reply.login.accepted=controller.accepted;
                reply.login.LobbyIsFull= controller.LobbyIsFull;
                reply.login.username= ObjCommand.username;
                String StrCommand = g.toJson(reply);
                out.println(StrCommand);
                if(!reply.login.accepted && reply.login.LobbyIsFull){
                    break;
                }

            case ("GAMEPLAY"):


            case ("CHAT"):
                reply.cmd="CHAT";
                controller.setChat(ObjCommand.chat.username, ObjCommand.chat.message);
                reply.chat.username = ObjCommand.chat.username;
                reply.chat.message = ObjCommand.chat.message;
                broadcast(reply);
        }

    }

}

