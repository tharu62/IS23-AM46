package it.polimi.ingsw.MODEL;

public class PLAYER {
    String username;
    public BOOKSHELF bookshelf= new BOOKSHELF();
    public PERSONAL_GOAL_CARD personal= new PERSONAL_GOAL_CARD();
    int score=0;
    boolean goalReached=false;

    /** STO MODIFICANDO **/
    public void drawPersonalGoalCard(P_CARD_LOGIC cardLogic){
        personal.SetCardLogic(cardLogic);
    }


    public String getUsername() {
        return username;
    }
}
