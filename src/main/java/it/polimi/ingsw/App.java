package it.polimi.ingsw;

import java.rmi.RemoteException;

public class App 
{
    public static void main( String[] args ) throws RemoteException, InterruptedException {
        SetUpper setUpper = new SetUpper();
        setUpper.run();
    }
}
