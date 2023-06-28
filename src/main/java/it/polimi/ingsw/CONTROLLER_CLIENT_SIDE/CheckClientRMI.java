package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;

import java.rmi.RemoteException;

public class CheckClientRMI extends Thread{
    public ClientRMI client;
    public CONTROLLER controller;
    @Override
    public void run(){

        while(true){

            synchronized (this){
                try {
                    wait(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                System.out.println("check ping");
                ClientRMI.gs.ping();
            } catch (RemoteException e) {
                try {
                    ClientRMI.gs.ping();
                } catch (RemoteException ex) {
                    System.out.println("restarting the client ");
                    try {
                        client = new ClientRMI(Settings.PORT_RMI, false);
                        client.disconnected = true;
                        controller.restartClient(client);
                    } catch (RemoteException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        }

    }
}
