package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;

import java.rmi.RemoteException;

public class CheckClientRMI extends Thread{
    public ClientRMI client;
    public CONTROLLER controller;
    int counter = 0;
    @Override
    public void run(){

        while(true){

            synchronized (this){
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(counter > 0){
                if(client.disconnected){
                    System.out.println("restarting the client ");
                    try {
                        client = new ClientRMI(Settings.PORT_RMI, false);
                        client.disconnected = true;
                        controller.restartClient(client);
                    } catch (Exception exc) {
                        throw new RuntimeException(exc);
                    }
                }
            } else {
                try {
                    ClientRMI.gs.ping(client);
                    client.disconnected = true;
                    counter ++;
                } catch (RemoteException e) {
                    System.out.println("restarting the client ");
                    try {
                        client = new ClientRMI(Settings.PORT_RMI, false);
                        client.disconnected = true;
                        controller.restartClient(client);
                    } catch (Exception exc) {
                        throw new RuntimeException(exc);
                    }
                }


            }


        }

    }
}
