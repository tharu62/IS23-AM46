package it.polimi.ingsw.MODEL;

public class PLAYER {
    String username;
    BOOKSHELF bookshelf= new BOOKSHELF();
    PERSONAL_GOAL_CARD personal= new PERSONAL_GOAL_CARD();
    int score=0;
    boolean goalReached=false;

    /** STO MODIFICANDO **/
    public void drawPersonalGoalCard(P_CARD_LOGIC cardLogic){
        personal.SetCardLogic(cardLogic);
    }


}
