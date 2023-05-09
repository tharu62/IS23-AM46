package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.GameClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

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

            //main loop TCP
            while (true) {
                String StrCommand = in.nextLine();
                Command ObjCommand = g.fromJson(StrCommand,Command.class);
                CommandSwitcher(ObjCommand);
                if(active){
                    out.println(reply_string);
                    active= false;
                    reply = null;
                    reply_string = null;
                }
                if(disconnect){
                   break;
                }

                /** PHASE 1
                 *  After the login phase the Server sends the Board, the Common_goal_cards and player_to_play.
                 */
                if(controller.game.master.round.turn.count == 0 && controller.LobbyIsFull && !controller.GameHasStarted){
                    Command temp;
                    for (GameClient gc : clientsRMI) {
                        gc.receiveBoard(controller.getBoard());
                        gc.receiveCommonGoals(controller.getCommonGoalCard());
                        gc.receivePlayerToPlay(controller.game.playerToPlay);
                    }

                    temp = new Command();
                    temp.cmd = "BOARD";
                    temp.broadcast.grid = controller.getBoard();
                    broadcast(temp);

                    temp = new Command();
                    temp.cmd = "COMMON_GOALS";
                    temp.broadcast.cards = controller.getCommonGoalCard();
                    broadcast(temp);

                    temp = new Command();
                    temp.cmd = "PLAYER_TO_PLAY";
                    temp.broadcast.ptp = controller.game.playerToPlay;
                    broadcast(temp);

                    controller.GameHasStarted = true;
                }

                /**
                 * PHASE 2
                 */
                if(controller.TurnHasStarted){
                    Timer timer = new Timer();
                    //TODO timerTask...
                }

                /** PHASE 3
                 * If it's the last round and the last turn, the game is over, the model autonomously calculate the scores
                 * and finds the winner.
                 */
                if(controller.game.master.round.last && controller.game.master.round.turn.count == (controller.game.playerNumber-1)){
                    controller.GameIsOver = true;
                    //temp = new Command();
                    //temp.cmd = "WINNER";
                    //temp.broadcast.ptp = controller.game.space.winner
                    //clientsTCP.get(0).broadcast(temp);

                }

                if(controller.GameIsOver){
                    break;
                }

            }
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

            case ("CONNECTED_REPLY"):
                reply = new Command();
                if(controller.setLogin(ObjCommand.username)){
                    reply.cmd = "REPLY_ACCEPTED";
                }else{
                    reply.cmd = "REPLY_NOT_ACCEPTED";
                }
                reply_string = g.toJson(reply);
                active= true;

            case ("FIRST_TO_CONNECT_REPLY"):
                reply = new Command();
                if(controller.setFirstLogin(ObjCommand.username, ObjCommand.login.LobbySize)){
                    reply.cmd = "REPLY_ACCEPTED";
                }else{
                    reply.cmd = "REPLY_NOT_ACCEPTED";
                }
                reply_string = g.toJson(reply);
                active= true;

            case ("SEND_PERSONAL_GOAL_CARD"):
                reply = new Command();
                reply.cmd = "PERSONAL_GOAL_CARD_REPLY";
                reply.gameplay.card = controller.getPersonalGoalCards(ObjCommand.username);
                reply_string = g.toJson(reply);
                active= true;

            case ("ASK_MY_TURN"):
                reply = new Command();
                if(controller.setTurn(ObjCommand.username)){
                    reply.cmd = "IT_IS_YOUR_TURN";
                }else{
                    reply.cmd = "IT_IS_NOT_YOUR_TURN";
                }
                reply_string = g.toJson(reply);
                active= true;

            case ("ASK_DRAW"):
                reply = new Command();
                if(controller.setDraw(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1))){
                    reply.cmd = "DRAW_VALID";
                }else{
                    reply.cmd = "DRAW_NOT_VALID";
                }
                reply_string = g.toJson(reply);
                active= true;

            case("ASK_PUT_ITEM"):
                reply = new Command();
                if(controller.setBookshelf(ObjCommand.username, ObjCommand.gameplay.pos.get(0), ObjCommand.gameplay.pos.get(1), ObjCommand.gameplay.pos.get(2), ObjCommand.gameplay.pos.get(3) )){
                    reply.cmd = "PUT_VALID";
                }else{
                    reply.cmd = "PUT_NOT_VALID";
                }
                reply_string = g.toJson(reply);
                active= true;

            case ("CHECK_SCORE"):
                reply = new Command();
                reply.cmd = "RETURN_SCORE";
                reply.gameplay.pos.add(controller.setScore(ObjCommand.username));
                reply_string = g.toJson(reply);
                active= true;

            case ("END_TURN"):
                reply = new Command();
                if(controller.setEndTurn(ObjCommand.username)){
                    reply.cmd = "PLAYER_TO_PLAY";
                    reply.broadcast.ptp = controller.game.playerToPlay;
                    broadcast(reply);
                }

            case ("CHAT"):
                reply = new Command();
                reply.cmd="CHAT";
                controller.setChat(ObjCommand.chat.username, ObjCommand.chat.message);
                broadcast(ObjCommand);

        }
    }
}

