package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class CHAT {
    public List<MESSAGE> chat= new ArrayList<>(1);

    /**
     * This method adds a message to the attribute List<MESSAGE> chat.
     * @param message is a class that contains an array of String 'header' and a String 'text'.
     */
    public void addMessage(MESSAGE message){
        chat.add(message);
    }

    /**
     * This method return the last message of the player with the given username. If there is no message
     * it returns a message with a header containing the string "no data" in both indexes and the text field containing
     * the string "no text from this player".
     * @param username is the username of a player.
     * @return
     */
    public MESSAGE returnLastMessage(String username){
        int i=chat.size();
        while(i > 0 && !chat.get(i-1).header[0].equals(username)){
            i--;
        }
        if(i > 0 && chat.get(i - 1).header[0].equals(username)){
            return chat.get(i - 1);
        }
        else{
            MESSAGE temp= new MESSAGE();
            temp.header[0]="no data";
            temp.header[1]="no data";
            temp.text="no text from this player";
            return temp;
        }
    }
}
