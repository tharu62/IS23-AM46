package it.polimi.ingsw;

import it.polimi.ingsw.RMI.ClientRMI;

import java.io.IOException;
import java.rmi.RemoteException;

public class CheckClientRMI extends Thread{
    ClientRMI client;
    @Override
    public void run(){

        while(true){
            if(!client.disconnected){
                try{
                    ClientRMI.gs.ping();
                }catch(RemoteException r){
                    client.controller.restartClient(client);
                }
            }
        }

    }
}
