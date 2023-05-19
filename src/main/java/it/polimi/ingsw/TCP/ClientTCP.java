package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP extends Thread {
    public String hostName = "127.0.0.1";
    public int PORT;
    public CONTROLLER controller;
    public Gson g= new Gson();
    public Command userInputObj= new Command();
    public String userInputStr;
    public Command reply = new Command();
    public String reply_string;
    public boolean active= false;
    public boolean serverDisconnected = true;

    public ClientTCP(CONTROLLER controller, int port){
        this.controller = controller;
        this.PORT = port;
    }

    @Override
    public void run() {
        try (
                Socket echoSocket = new Socket(hostName, PORT);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
            ) {

            while (true) {
                //userInputStr =  g.toJson(userInputObj);
                //out.println(userInputStr);

                // wait for reply from server
                String StrCommand = in.readLine();
                Command ObjCommand = g.fromJson(StrCommand,Command.class);
                CommandSwitcher(ObjCommand);
                if(active){
                    out.println(reply_string);
                    active = false;
                }
                if(controller.LobbyIsFull){
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            controller.notifyCLI("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    /** Logic to check all possible messages from the server, it does not give an instant reply,
     *  instead the reply is give by the user by user input.
     * @param ObjCommand is the object that contains all the message types and data.
     */
    private void CommandSwitcher(Command ObjCommand){
        switch (ObjCommand.cmd){
            case FIRST_TO_CONNECT:
                controller.notifyCLI(ObjCommand.cmd.toString());
                reply = new Command();
                reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                reply.username = controller.getUsername();
                reply.login.LobbySize = controller.getLobbySize();
                reply_string = g.toJson(reply);
                active = true;
                controller.firstToConnect = true;

            case CONNECTED:
                controller.notifyCLI(ObjCommand.cmd.toString());
                reply = new Command();
                reply.cmd = CMD.CONNECTED_REPLY;
                reply.username = controller.getUsername();
                reply_string = g.toJson(reply);
                active = true;

            case REPLY_ACCEPTED:
                controller.notifyCLI(ObjCommand.cmd.toString());
                controller.LoginAccepted = true;

            case REPLY_NOT_ACCEPTED:
                if(controller.firstToConnect){
                    controller.notifyCLI(ObjCommand.cmd.toString());
                    reply = new Command();
                    reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                    reply.username = controller.getUsername();
                    reply.login.LobbySize = controller.getLobbySize();
                    reply_string = g.toJson(reply);
                    active = true;
                    controller.firstToConnect = true;
                }else{
                    controller.notifyCLI(ObjCommand.cmd.toString());
                    reply = new Command();
                    reply.cmd = CMD.CONNECTED_REPLY;
                    reply.username = controller.getUsername();
                    reply_string = g.toJson(reply);
                    active = true;
                }

            case LOBBY_IS_FULL:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case BOARD:
                controller.setBoard(ObjCommand.broadcast.grid);
                controller.notifyCLI(ObjCommand.cmd.toString());

            case COMMON_GOALS:
                controller.setCommonGoals(ObjCommand.broadcast.cards);
                controller.notifyCLI(ObjCommand.cmd.toString());

            case PLAYER_TO_PLAY:
                controller.setPlayerToPlay(ObjCommand.broadcast.ptp);
                controller.notifyCLI(ObjCommand.cmd.toString());

            case PERSONAL_GOAL_CARD_REPLY:
                controller.setPersonalGoal(ObjCommand.gameplay.card);
                controller.notifyCLI(ObjCommand.cmd.toString());

            case IT_IS_YOUR_TURN:
                controller.myTurn= true;
                controller.notifyCLI(ObjCommand.cmd.toString());

            case IT_IS_NOT_YOUR_TURN:
                controller.myTurn= false;
                controller.notifyCLI(ObjCommand.cmd.toString());

            case DRAW_VALID:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case DRAW_NOT_VALID:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case PUT_VALID:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case PUT_NOT_VALID:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case RETURN_SCORE:
                controller.notifyCLI(ObjCommand.cmd.toString());

            case CHAT:
                controller.notifyCLI(ObjCommand.cmd.toString());

        }
    }
}

