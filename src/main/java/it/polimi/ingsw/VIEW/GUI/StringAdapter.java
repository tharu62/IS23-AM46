package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.MESSAGE;

public class StringAdapter {
    public void splitPublic(GUI gui, MESSAGE message, int splitValue){
        if( message.text.length() > splitValue){
            int x = 0;
            int y = splitValue;
            while(x+1 < message.text.length()) {
                char[] temp = new char[splitValue];
                message.text.getChars(x, x + y, temp, 0);
                MESSAGE m = new MESSAGE();
                m.text = String.valueOf(temp);
                GUI.cmd.scrollChat(m, false);
                x += splitValue;
                if ((message.text.length() - x) <= splitValue) {
                    y = message.text.length() - x;
                }
            }
        }else{
            GUI.cmd.scrollChat(message,false);
        }
    }

    public void splitPrivate(GUI gui, MESSAGE message, int splitValue){
        if( message.text.length() > splitValue){
            int x = 0;
            int y = splitValue;
            while(x+1 < message.text.length()) {
                char[] temp = new char[splitValue];
                message.text.getChars(x, x + y, temp, 0);
                MESSAGE m = new MESSAGE();
                m.text = String.valueOf(temp);
                GUI.cmd.scrollChat(m, true);
                x += splitValue;
                if ((message.text.length() - x) <= splitValue) {
                    y = message.text.length() - x;
                }
            }
        }else{
            GUI.cmd.scrollChat(message,true);
        }
    }

}

