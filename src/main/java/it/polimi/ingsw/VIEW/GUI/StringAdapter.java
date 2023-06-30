package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.MESSAGE;

public class StringAdapter {

    /**
     * This method splits the message given in many messages of the given split value and sends them to the GUI scene
     * and to the server. (public message only)
     *
     * @param gui is the reference to the GUI.
     * @param message is the message to split.
     * @param splitValue it's the value at which the string is split.
     */
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


    /**
     * This method splits the message given in many messages of the given split value and sends them to the GUI scene
     * and to the server. (private message only)
     *
     * @param gui is the reference to the GUI.
     * @param message is the message to split.
     * @param splitValue it's the value at which the string is split.
     */
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

