package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER.game.client.ActionSender;
import it.polimi.ingsw.MODEL.GAME;

public class Server_App {
    public static void main( String[] args){
        GAME game= new GAME();
        ActionSender controller= new ActionSender();            /** Server-side Controller **/
        server server = new server(controller,1234);
        server.start();
    }
}
