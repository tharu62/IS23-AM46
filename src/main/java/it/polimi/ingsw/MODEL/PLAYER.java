package it.polimi.ingsw.MODEL;

public class PLAYER {
    String username;
    public BOOKSHELF bookshelf= new BOOKSHELF();
    public PERSONAL_GOAL_CARD personal= new PERSONAL_GOAL_CARD();
    public boolean disconnected = false;
    public int score=0;
    public boolean[] goalReached = {false, false};

    public void drawPersonalGoalCard(P_CARD_LOGIC cardLogic){
        personal.SetCardLogic(cardLogic);
    }
    public String getUsername() {
        return username;
    }
}
