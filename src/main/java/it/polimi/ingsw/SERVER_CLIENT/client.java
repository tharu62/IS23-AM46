package it.polimi.ingsw.SERVER_CLIENT;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class client {
    public String hostName = "127.0.0.1";
    public int portNumber = 1234;
    public CONTROLLER controller;
    public client(CONTROLLER controller){
        this.controller= controller;
    }
    public void start() {

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ) {

            Gson g= new Gson();
            Command userInputObj= new Command();
            String userInputStr;
            while (true) {

                /** Waits for command from the user.
                 * For example:
                 * userInputObj= controller.NextMove();
                 */
                userInputStr =  g.toJson(userInputObj);
                out.println(userInputStr);            /** send input to server**/



                String StrCommand = in.readLine();    /** wait for reply from server**/
                Command ObjCommand = g.fromJson(StrCommand,Command.class);
                /** Send the reply object to controller.
                 *  For example:
                 *  controller.checkReply(objCommand)
                 */

            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

    }
}

