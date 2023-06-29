package it.polimi.ingsw.MODEL;

import java.io.Serializable;

public class MESSAGE implements Serializable {

    /** first string in array is the username of the sender, second is the receiver**/
    public String[] header = new String[2];

    public String text;

}
