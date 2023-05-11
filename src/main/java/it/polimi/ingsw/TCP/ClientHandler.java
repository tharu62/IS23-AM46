package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.GameClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private CONTROLLER controller;
    private List<ClientHandler> clients;
    private  List<GameClient> clientsRMI;

    public PrintWriter out;
    public Command reply;
    public String reply_string;
    public Gson g;
    public boolean active= false;
    public boolean disconnect= false;

    public ClientHandler(Socket socket, CONTROLLER controller, List<ClientHandler> clients, List<GameClient> clientsRMI ) {
        this.socket = socket;
        this.controller = controller;
        this.clients = clients;
        this.clientsRMI = clientsRMI;
        clients.add(this);
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            do {
                String StrCommand = in.nextLine();
                Command ObjCommand = g.fromJson(StrCommand, Command.class);

                CommandSwitcher(ObjCommand);

                if (active) {
                    out.println(reply_string);
                    active = false;
                    reply = null;
                    reply_string = null;
                }
                if (disconnect) {
                    //TODO resilience to disconnection.
                }

            } while (!controller.GameIsOver);

            // Socket is closed
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    synchronized public void broadcast(Command message){
        String temp= g.toJson(message);
        for (ClientHandler client : clients) {
            client.out.println(message);
        }
    }

    synchronized private void CommandSwitcher(Command ObjCommand){
        switch (ObjCommand.cmd){

            case CONNECTED_REPLY:
                reply = new Command();
                if(controller.setLogin(ObjCommand.username)){
                    reply.cmd = CMD.REPLY_ACCEPTED;
                }else{
                    reply.cmd = CMD.REPLY_NOT_ACCEPTED;
                }
                reply_string = g.toJson(reply);
                active= true;

            case FIRST_TO_CONNECT_REPLY:
                reply = new Command();
                if(controller.setFirstLogin(ObjCommand.username, ObjCommand.login.LobbySize)){
                    reply.cmd = CMD.REPLY_ACCEPTED;
                }else{
                    reply.cmd = CMD.REPLY_NOT_ACCEPTED;
                }
                reply_string = g.toJson(reply);
                active= true;

            case SEND_PERSONAL_GOAL_CARD:
                reply = new Command();
                reply.cmd = CMD.PERSONAL_GOAL_CARD_REPLY;
                reply.gameplay.card = controller.getPersonalGoalCards(ObjCommand.username);
                reply_string = g.toJson(reply);
                active= true;

            case ASK_MY_TURN:
                reply = new Command();
                if(controller.setTurn(ObjCommand.username)){
                    reply.cmd = CMD.IT_IS_YOUR_TURN;
                }else{
                    reply.cmd = CMD.IT_IS_NOT_YOUR_TURN;
                }
                reply_string = g.toJson(reply);
                active= true;

            case ASK_DRAW:
                reply = new Command();
                if(controller.setDraw(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1))){
                    reply.cmd = CMD.DRAW_VALID;
                }else{
                    reply.cmd = CMD.DRAW_NOT_VALID;
                }
                reply_string = g.toJson(reply);
                active= true;

            case ASK_PUT_ITEM:
                reply = new Command();
                if(controller.setBookshelf(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1), ObjCommand.gameplay.pos.get(2), ObjCommand.gameplay.pos.get(3) )){
                    reply.cmd = CMD.PUT_VALID;
                }else{
                    reply.cmd = CMD.PUT_NOT_VALID;
                }
                reply_string = g.toJson(reply);
                active= true;

            case CHECK_SCORE:
                reply = new Command();
                reply.cmd = CMD.RETURN_SCORE;
                reply.gameplay.pos.add(controller.setScore(ObjCommand.username));
                reply_string = g.toJson(reply);
                active= true;

            case END_TURN:
                reply = new Command();
                if(controller.setEndTurn(ObjCommand.username)){
                    reply.cmd = CMD.PLAYER_TO_PLAY;
                    reply.broadcast.ptp = controller.game.playerToPlay;
                    broadcast(reply);
                }

            case CHAT:
                reply = new Command();
                reply.cmd= CMD.CHAT;
                controller.setChat(ObjCommand.chat.username, ObjCommand.chat.message);
                broadcast(ObjCommand);

        }
    }
}

