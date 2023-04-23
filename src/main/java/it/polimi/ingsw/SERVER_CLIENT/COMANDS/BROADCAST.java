package it.polimi.ingsw.SERVER_CLIENT.COMANDS;

import it.polimi.ingsw.MODEL.CARD_LOGIC;
import it.polimi.ingsw.MODEL.TOKEN;
import it.polimi.ingsw.MODEL.item;

public class BROADCAST {

    /** indicates what type of message is being sent and so what data to read from the BROADCAST object **/
    public String cmd;


    /** BOARD **/
    public item[][] grid;


    /** COMMON_GOAL_CARDS **/
    public CARD_LOGIC cardLogic;
    public TOKEN token;
    public int token_value;


    /** FIRST_PLAYER_SEAT **/
    public String fps;

}
