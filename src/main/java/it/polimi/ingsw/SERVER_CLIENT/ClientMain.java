package it.polimi.ingsw.SERVER_CLIENT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.UnknownHostException;


public class ClientMain {
    public static void main(String[] args) throws IOException {

        String hostName = "127.0.0.1";
        int portNumber = 1234;
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {  /** Runs when the User of the client writes something on terminal  **/
                out.println(userInput);                       /** It prints on terminal the userInput from the terminal in the inputBuffer **/
                System.out.println("echo: " + in.readLine()); /** It prints on terminal the response (echo) from the Server in the inputBuffer **/
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
