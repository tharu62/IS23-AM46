package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandlerTCP extends Thread {
    private final Socket socket;
    public CONTROLLER controller;
    public List<ClientHandlerTCP> clients;

    public PrintWriter out;
    public Command reply;
    public String reply_string;
    public Gson g = new Gson();
    public boolean active = false;
    public String username;

    public ClientHandlerTCP(Socket socket, CONTROLLER controller, List<ClientHandlerTCP> clients ) {
        this.socket = socket;
        this.controller = controller;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            System.out.println(" A NEW CLIENT_TCP HAS CONNECTED! ");
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            if(controller.getCurrentPlayers() == 0){
                clients.add(this);
                controller.players++;
                reply = new Command();
                reply.cmd = CMD.FIRST_TO_CONNECT;
                reply_string = g.toJson(reply);
                out.println(reply_string);
            }
            else{
                if(!controller.getLobbyIsFull()){
                    clients.add(this);
                    controller.players++;
                    reply = new Command();
                    reply.cmd = CMD.CONNECTED;
                    reply_string = g.toJson(reply);
                    out.println(reply_string);
                }else{
                    reply = new Command();
                    reply.cmd = CMD.LOBBY_IS_FULL;
                    reply_string = g.toJson(reply);
                    out.println(reply_string);
                }
            }

            String StrCommand;
            Command ObjCommand;
            do {
                while( (StrCommand = in.nextLine()) != null ) {
                    ObjCommand = g.fromJson(StrCommand, Command.class);
                    CommandSwitcher(ObjCommand);

                    if (active) {
                        out.println(reply_string);
                        active = false;
                        reply = null;
                        reply_string = null;
                    }
                }
            } while (!controller.GameIsOver);

            // Socket is closed
            in.close();
            out.close();
            socket.close();

        } catch (IOException | NoSuchElementException e) {
            try {
                if(this.username == null){
                    controller.players--;
                    clients.remove(this);
                }else{
                    controller.disconnected(this.username);
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    synchronized public void broadcast(Command message){
        String temp = g.toJson(message);
        for (ClientHandlerTCP client : clients) {
            client.out.println(temp);

        }
    }

    synchronized public void CommandSwitcher(Command ObjCommand) throws RemoteException {
        switch (ObjCommand.cmd){

            case CONNECTED_REPLY:
                reply = new Command();
                this.username = ObjCommand.username;
                if(controller.setLogin(ObjCommand.username)){
                    reply.cmd = CMD.REPLY_ACCEPTED;
                }else{
                    this.username = null;
                    reply.cmd = CMD.REPLY_NOT_ACCEPTED;
                }
                reply_string = g.toJson(reply);
                active = true;
                break;

            case FIRST_TO_CONNECT_REPLY:
                reply = new Command();
                this.username = ObjCommand.username;
                if(controller.setFirstLogin(ObjCommand.username, ObjCommand.login.LobbySize)){
                    reply.cmd = CMD.REPLY_ACCEPTED;
                }else{
                    this.username = null;
                    reply.cmd = CMD.REPLY_NOT_ACCEPTED;
                }
                reply_string = g.toJson(reply);
                active= true;
                break;

            case RECONNECTED_REPLY:
                reply = new Command();
                if(controller.setLoginReconnection(ObjCommand.username)){
                    this.username = ObjCommand.username;
                    clients.add(this);
                    reply.cmd = CMD.REPLY_ACCEPTED;

                }else{
                    reply.cmd = CMD.REPLY_NOT_ACCEPTED;
                }
                break;

            case ASK_DRAW:
                reply = new Command();
                if(controller.setDraw(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1))){
                    reply.cmd = CMD.DRAW_VALID;
                }else{
                    reply.cmd = CMD.DRAW_NOT_VALID;
                }
                reply_string = g.toJson(reply);
                active = true;
                break;

            case ASK_PUT:
                reply = new Command();
                if(controller.setBookshelf(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1), ObjCommand.gameplay.pos.get(2), ObjCommand.gameplay.pos.get(3) )){
                    reply.cmd = CMD.PUT_VALID;
                }else{
                    reply.cmd = CMD.PUT_NOT_VALID;
                }
                reply_string = g.toJson(reply);
                active= true;
                break;

            case END_TURN:
                controller.setEndTurn(ObjCommand.username);
                break;

            case FROM_CLIENT_CHAT:
                reply = new Command();
                reply.cmd= CMD.FROM_SERVER_CHAT;
                controller.setChat(ObjCommand.chat.message);
                break;

            case FROM_SERVER_CHAT:
                reply_string = g.toJson(ObjCommand);
                out.println(reply_string);
                break;

            case SEND_RECONNECTION_DATA:
                controller.sendReconnectionData(ObjCommand.username);
                break;
        }
    }
}

