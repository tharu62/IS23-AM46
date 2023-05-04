package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.TCP.COMANDS.LOGIN;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private CONTROLLER controller;
    private List<ClientHandler> clients;
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
                if(ObjCommand.cmd == "FIRST_TO_CONNECT"){
                    controller.setFirstLogin(ObjCommand.login.username, ObjCommand.login.Lobbysize);
                }
                if(ObjCommand.cmd == "CONNECTED"){
                    controller.setLogin(ObjCommand.login.username);
                }
                if(ObjCommand.cmd == "LOBBY_IS_FULL"){
                    //todo
                }

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

