package it.polimi.ingsw;

import it.polimi.ingsw.CLI.CLI;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws RemoteException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CLI c = new CLI();
        executor.submit(c.input_loop());
        if(c.selectedGUI){
            GUI g = new GUI();
            //TODO
        }

    }
}
