package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.TCP.COMANDS.LOGIN;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ClientTCP extends Thread {
    public int PORT;
    public CONTROLLER controller;
    public Gson g = new Gson();
    public Command reply = new Command();
    public String reply_string;
    public PrintWriter out_ref;

    public ClientTCP( int port){
        this.PORT = port;
    }

    @Override
    public void run() {
        try (
                Socket Socket = new Socket(Settings.SERVER_NAME, PORT);
                PrintWriter out = new PrintWriter(Socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(Socket.getInputStream()))
            ) {
            String StrCommand;
            Command ObjCommand;
            out_ref = out;
            // wait for command from server
            while( (StrCommand = in.readLine()) != null ) {
                ObjCommand = g.fromJson(StrCommand, Command.class);
                CommandSwitcher(ObjCommand, out);
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + Settings.SERVER_NAME);
            System.exit(1);
        } catch (IOException e) {
            controller.notifyCLI("Couldn't get I/O for the connection to " + Settings.SERVER_NAME);
            System.exit(1);
        }
    }

    /** Logic to check all possible messages from the server, it does not give an instant reply,
     *  instead the reply is given by the user by user input.
     * @param ObjCommand is the object that contains all the message types and data.
     */
    synchronized public void CommandSwitcher(Command ObjCommand , PrintWriter out) throws RemoteException {
        switch (ObjCommand.cmd){
            case FIRST_TO_CONNECT:
                controller.firstToConnect = true;
                controller.notifyCLI(ObjCommand.cmd.toString());
                reply = new Command();
                reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                reply.username = controller.getUsername();
                reply.login = new LOGIN();
                reply.login.LobbySize = controller.getLobbySize();
                reply_string = g.toJson(reply);
                out.println(reply_string);
                break;

            case CONNECTED:
                controller.notifyCLI(ObjCommand.cmd.toString());
                reply = new Command();
                reply.cmd = CMD.CONNECTED_REPLY;
                reply.username = controller.getUsername();
                reply_string = g.toJson(reply);
                out.println(reply_string);
                break;

            case REPLY_ACCEPTED:
                controller.LoginOK = true;
                break;

            case REPLY_NOT_ACCEPTED:
                if(controller.firstToConnect){
                    controller.notifyCLI(ObjCommand.cmd.toString());
                    reply = new Command();
                    reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                    reply.username = controller.getUsername();
                    reply.login = new LOGIN();
                    reply.login.LobbySize = controller.getLobbySize();
                    reply_string = g.toJson(reply);
                    out.println(reply_string);
                }else{
                    controller.notifyCLI(ObjCommand.cmd.toString());
                    reply = new Command();
                    reply.cmd = CMD.CONNECTED_REPLY;
                    reply.username = controller.getUsername();
                    reply_string = g.toJson(reply);
                    out.println(reply_string);
                }
                break;

            case LOBBY_IS_FULL:
                controller.notifyCLI(ObjCommand.cmd.toString());
                break;

            case BOARD:
                controller.setBoard(ObjCommand.broadcast.grid);
                break;

            case COMMON_GOALS:
                controller.setCommonGoals(ObjCommand.broadcast.cardsID.get(0), ObjCommand.broadcast.cardsValue.get(0));
                controller.setCommonGoals(ObjCommand.broadcast.cardsID.get(1), ObjCommand.broadcast.cardsValue.get(1));
                break;

            case PLAYER_TO_PLAY:
                controller.setPlayerToPlay(ObjCommand.broadcast.ptp);
                break;

            case PERSONAL_GOAL_CARD_REPLY:
                controller.setPersonalGoal(ObjCommand.gameplay.cardID);
                break;

            case BOOKSHELF:
                controller.bookshelf = ObjCommand.gameplay.bookshelf;
                controller.bookshelf_received = true;
                break;

            case DRAW_VALID:
                controller.draw_valid = true;
                controller.reply_draw = true;
                break;

            case DRAW_NOT_VALID:
                controller.draw_valid = false;
                controller.reply_draw = true;
                break;

            case PUT_VALID:
                controller.reply_put = true;
                controller.put_valid = true;
                break;

            case PUT_NOT_VALID:
                controller.reply_put = true;
                controller.put_valid = false;
                break;

            case PLAYERS:
                controller.players = ObjCommand.broadcast.players;
                break;

            case LAST_ROUND:
                controller.setLastRound();
                break;

            case RETURN_SCORE:
                //TODO
                break;

            case FROM_SERVER_CHAT:
                controller.receiveChat(ObjCommand.chat.message);
                break;

            case FROM_CLIENT_CHAT, ASK_DRAW, ASK_PUT_ITEM , ASK_BOOKSHELF , END_TURN , ASK_MY_TURN, SEND_PERSONAL_GOAL_CARD:
                reply = ObjCommand;
                reply_string = g.toJson(reply);
                out.println(reply_string);
                break;

            case WINNER:
                controller.notifyCLI(" THE GAME IS OVER, THE WINNER IS '"+ ObjCommand.username +"'");
                break;

            case USER_DISCONNECTED:
                controller.notifyCLI(" The player '" + ObjCommand.username + "' has disconnected, the match is over by default.");
                controller.notifyCLI(" AUTO-SHUTDOWN ...");
                synchronized (this){
                    try {
                        this.wait(3000);
                    }catch (InterruptedException e){
                        System.exit(0);
                    }
                }
                System.exit(0);
        }
    }
}

