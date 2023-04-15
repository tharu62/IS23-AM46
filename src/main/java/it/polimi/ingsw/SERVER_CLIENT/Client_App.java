package it.polimi.ingsw.SERVER_CLIENT;

import it.polimi.ingsw.CONTROLLER.game.client.ActionSender;

public class Client_App {
    public static void main( String[] args){
        ActionSender controller= new ActionSender();            /** Client-side Controller **/
        client client= new client(controller);
        client.start();
    }
}
