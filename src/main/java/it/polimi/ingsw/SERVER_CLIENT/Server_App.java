package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;

public class Server_App {
    public static void main( String[] args){
        GAME game= new GAME();
        CONTROLLER controller= new CONTROLLER();    /** Server-side Controller **/
        controller.setGame(game);
        server server = new server(controller,1234);
        server.start();
    }
}
