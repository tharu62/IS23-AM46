package it.polimi.ingsw;

import it.polimi.ingsw.CLI.CLI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CLI c = new CLI();
        c.input_loop();
        if(c.selectedGUI){
            GUI g = new GUI();
            //TODO
        }

    }
}
