package it.polimi.ingsw.NETWORK;

/**
 * This class contains the necessary information to start the Server AND to connect the Clients:
 * PORT_<communication_protocol> : It the local port of the Server that implements the <communication_protocol>.
 * SERVER_NAME : it's the IP address of the Sever.
 */
public class Settings {
    public static int PORT_RMI = 1;
    public static int PORT_TCP = 2;
    public static String SERVER_NAME = "127.0.0.1";
}
