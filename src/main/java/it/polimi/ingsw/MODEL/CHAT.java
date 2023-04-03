package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class CHAT {
    List<MESSAGE> chat= new ArrayList<MESSAGE>(1);

    public void addMessage(MESSAGE message){
        chat.add(message);
    }

    public List<String> returnLastMessage(String username){
        int i=chat.size();
        while(!chat.get(i-1).header[1].equals(username)){
            i--;
        }
        if(chat.get(i).header[1].equals(username)){
            return chat.get(i).text;
        }
        else{
            String Exception="no text from this player";
            List<String> temp= new ArrayList<String>();
            temp.add(Exception);
            return temp;
        }
    }
}
