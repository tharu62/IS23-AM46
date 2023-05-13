package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class CHAT {
    public List<MESSAGE> chat= new ArrayList<>(1);

    public void addMessage(MESSAGE message){
        chat.add(message);
    }

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
            temp.header[0]="no username in list";
            temp.header[1]="no data";
            temp.text="no text from this player";
            return temp;
        }
    }
}
