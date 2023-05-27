package it.polimi.ingsw.MODEL;

import java.io.Serializable;

public class MESSAGE implements Serializable {
    public String[] header = new String[2];     /** first string in array is the username of the sender, second is the receiver**/
    public String text;
}
