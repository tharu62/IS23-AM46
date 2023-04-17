package it.polimi.ingsw.SERVER_CLIENT;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER.game.client.ActionSender;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ActionSender controller;
    Gson g;
    public ClientHandler(Socket socket, CONTROLLER controller) {
        this.socket = socket;
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            while (true) {
                String StrCommand = in.nextLine();
                Command ObjCommand = g.fromJson(StrCommand,Command.class);

                /** Now the controller can handle the Command Objects and return unique messages to the clients
                 *  For example:
                 *  Command reply;
                 *  reply = controller.check(ObjCommand);
                 *  strCommand = g.toJson(reply);   /always chat with strings/
                 *  out.println(StrCommand);
                 */
                if(true){   /** if(!controller.login(username)) **/
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
}
