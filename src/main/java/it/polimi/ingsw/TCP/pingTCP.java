package it.polimi.ingsw.TCP;

import com.google.gson.Gson;
import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.List;

public class pingTCP extends Thread {
    CONTROLLER controller;
    List<ClientHandlerTCP> clients;
    Gson g = new Gson();
    String gString;
    int counter = 0;

    public pingTCP(CONTROLLER controller, List<ClientHandlerTCP> clients) {
        this.controller = controller;
        this.clients = clients;
    }
    @Override
    public void run(){
        while(true) {

            synchronized (this){
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(clients != null ) {
                if (counter > 0 && clients.get(counter - 1).disconnected && clients.get(counter-1).username != null) {
                    try {
                        System.out.println(" The player <" + clients.get(counter - 1).username + "> has disconnected ");
                        controller.disconnected(clients.get(counter - 1).username);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (counter == clients.size() || counter > clients.size()) {
                    counter = 0;
                } else {
                    Command command = new Command();
                    command.cmd = CMD.PING;
                    gString = g.toJson(command);
                    clients.get(counter).out.println(gString);
                    clients.get(counter).disconnected = true;
                    counter++;
                }
            }

        }
    }

}
