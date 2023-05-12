package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientTCP {
    public String hostName = "127.0.0.1";
    public int portNumber = 1234;
    public CONTROLLER controller;
    public Gson g= new Gson();
    public Command userInputObj= new Command();
    public String userInputStr;
    public Command reply = new Command();
    public String reply_string;
    public boolean active= false;
    public ClientTCP(CONTROLLER controller){
        this.controller= controller;
    }
    public void start() {

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
            ) {



            while (true) {

                /** Waits for command from the user.
                 * For example:
                 * userInputObj= controller.NextMove();
                 */
                userInputStr =  g.toJson(userInputObj);

                /** send input to server**/
                out.println(userInputStr);


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
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

    }

    /** Logic to check all possible messages from the server, it does not give an instant reply,
     *  instead the reply is give by the user by user input.
     *
     * @param ObjCommand is the object that contains all the message types and data.
     */
    private void CommandSwitcher(Command ObjCommand){
        switch (ObjCommand.cmd){
            case FIRST_TO_CONNECT:
                //TODO
                // ask player for username and LobbySize by VIEW
                reply = new Command();
                reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                reply.username = controller.username;
                reply.login.LobbySize = controller.LobbySize;
                reply_string = g.toJson(reply);
                active = true;
                controller.firstToConnect = true;

            case CONNECTED:
                //TODO
                // ask player for username by VIEW
                reply = new Command();
                reply.cmd = CMD.CONNECTED_REPLY;
                reply.username = controller.username;
                reply_string = g.toJson(reply);
                active = true;

            case REPLY_ACCEPTED:
                //TODO
                // notify VIEW
                controller.LoginAccepted = true;

            case REPLY_NOT_ACCEPTED:
                if(controller.firstToConnect){
                    //TODO
                    // ask player for username and LobbySize by VIEW
                    reply = new Command();
                    reply.cmd = CMD.FIRST_TO_CONNECT_REPLY;
                    reply.username = controller.username;
                    reply.login.LobbySize = controller.LobbySize;
                    reply_string = g.toJson(reply);
                    active = true;
                    controller.firstToConnect = true;
                }else{
                    //TODO
                    // ask player for username by VIEW
                    reply = new Command();
                    reply.cmd = CMD.CONNECTED_REPLY;
                    reply.username = controller.username;
                    reply_string = g.toJson(reply);
                    active = true;
                }

            case LOBBY_IS_FULL:
                //TODO
                // notify VIEW

            case BOARD:
                controller.setBoard(ObjCommand.broadcast.grid);
                //TODO
                // notify VIEW

            case COMMON_GOALS:
                controller.setCommonGoals(ObjCommand.broadcast.cards);
                //TODO
                // notify VIEW

            case PLAYER_TO_PLAY:
                controller.setPlayerToPlay(ObjCommand.broadcast.ptp);
                //TODO
                // notify VIEW

            case PERSONAL_GOAL_CARD_REPLY:
                controller.setPersonalGoal(ObjCommand.gameplay.card);
                //TODO
                // notify VIEW

            case IT_IS_YOUR_TURN:
                controller.myTurn= true;
                //TODO
                // notify VIEW

            case IT_IS_NOT_YOUR_TURN:
                controller.myTurn= false;
                //TODO
                // notify VIEW

            case DRAW_VALID:
                //TODO
                // notify VIEW

            case DRAW_NOT_VALID:
                //TODO
                // notify VIEW

            case PUT_VALID:
                //TODO
                // notify VIEW

            case PUT_NOT_VALID:
                //TODO
                // notify VIEW

            case RETURN_SCORE:
                //TODO
                // notify VIEW

            case CHAT:
                //TODO
                // notify VIEW

        }
    }
}

