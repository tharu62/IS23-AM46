package it.polimi.ingsw.MODEL;

public class PLAYER {
    String username= new String();
    BOOKSHELF bookshelf= new BOOKSHELF();
    PERSONAL_GOAL_CARD personal= new PERSONAL_GOAL_CARD();
    P_CARD_LOGIC_GENERATOR generator= new P_CARD_LOGIC_GENERATOR();
    int score=0;
    boolean goalReached=false;

    /** STO MODIFICNADO **/
    public void drawPersonalGoalCard(){
        personal.SetCardLogic(generator.SetCardLogic());
    }

}
