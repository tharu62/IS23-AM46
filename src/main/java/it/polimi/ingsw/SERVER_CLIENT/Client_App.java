package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;

public class Client_App {
    public static void main( String[] args){
        CONTROLLER controller= new CONTROLLER();            /** Client-side Controller **/
        client client= new client(controller);
        client.start();
    }
}
