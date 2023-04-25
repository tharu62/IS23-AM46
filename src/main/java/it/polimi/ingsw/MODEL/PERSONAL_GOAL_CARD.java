package it.polimi.ingsw.MODEL;

public class PERSONAL_GOAL_CARD extends PERSONAL_GOALS {
    P_CARD_LOGIC cardLogic;

    public void SetCardLogic(P_CARD_LOGIC logic) {
        this.cardLogic = logic;
    }

    public int checkCardLogic(BOOKSHELF bookshelf) {
        return this.cardLogic.checkCardLogic(bookshelf);
    }

}
