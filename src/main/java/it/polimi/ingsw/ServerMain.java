package it.polimi.ingsw;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class ServerMain {
    static int portNumber = 1234;
    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

}
