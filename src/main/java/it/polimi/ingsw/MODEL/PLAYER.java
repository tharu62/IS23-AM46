package it.polimi.ingsw.MODEL;

public class PLAYER {
    String username;
    public BOOKSHELF bookshelf= new BOOKSHELF();
    public PERSONAL_GOAL_CARD personal= new PERSONAL_GOAL_CARD();
    public int score = 0;
    public boolean[] goalReached = {false, false};

    /**
     * This method sets the unique cardLogic of the personal goal card of the player.
     * @param cardLogic is the logic (essentially a method) to check if (and by how much) the player has achieved
     *                  the goal of the personal goal card.
     */
    public void drawPersonalGoalCard(P_CARD_LOGIC cardLogic){
        personal.SetCardLogic(cardLogic);
    }

    /**
     * This method returns the username of the player.
     * @return username.
     */
    public String getUsername() {
        return username;
    }
}
