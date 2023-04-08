package it.polimi.ingsw;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {

    static int portNumber = 1234;
    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket= new Socket();

        System.out.println("Accepting..");
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Accepted");
        } catch (IOException e){
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}