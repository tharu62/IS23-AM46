package it.polimi.ingsw.SERVER_CLIENT;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;        /**LIBRERIA AGGIUNTA A MANO**/

public class MultiEchoServer {
    private int port;
    Gson g = new Gson();
    private String jsonString;

    private String json = g.toJson(jsonString);         /** Object Command serialized to a String jsonString **/
    Command c = g.fromJson(jsonString, Command.class);  /** Object Command deserialized from a String jsonString **/
    public MultiEchoServer(int port) {
        this.port = port;
    }
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new EchoServerClientHandler(socket));
            } catch(IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }

    /** The main function instantiate the Server and gives it the '1234' local port.
     *  The Server is started, it waits for a Client message than it gives a reply (echo for now).
     * @param args
     */
    public static void main(String[] args) {
        MultiEchoServer echoServer = new MultiEchoServer(1234);
        echoServer.startServer();
    }
}


