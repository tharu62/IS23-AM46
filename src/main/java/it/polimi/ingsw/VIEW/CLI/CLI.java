package it.polimi.ingsw.VIEW.CLI;

import java.util.Scanner;

public class CLI extends Thread implements CLI_Interface{
    boolean something = false;
    @Override
    public void run(){
        while(true){
            if(something){
                System.out.println(" Shutdown... ");
                break;
            }
            //TODO input cases from player...
        }
    }

    @Override
    public void notify(String message) {
        System.out.println( message );
    }

    @Override
    public String getUsername() {
        System.out.println(" Type your Username: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public int getLobbySize() {
        System.out.println(" Type your Username: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
