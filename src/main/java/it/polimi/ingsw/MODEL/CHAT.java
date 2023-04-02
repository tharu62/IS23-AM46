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
        while(chat.get(i).header[1]!=username){
            i--;
        }
        return chat.get(i).text;
    }
}
