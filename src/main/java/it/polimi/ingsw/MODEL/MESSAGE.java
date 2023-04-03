package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class MESSAGE {
    String[] header= new String[2];     /** first string in array is the username, second is date of release of the message**/
    List<String> text= new ArrayList<String>(0); /** each string is a word separated by a space **/
}
