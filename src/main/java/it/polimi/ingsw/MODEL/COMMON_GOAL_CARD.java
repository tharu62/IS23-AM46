package it.polimi.ingsw.MODEL;

public class COMMON_GOAL_CARD extends COMMON_GOALS {
    CARD_LOGIC cardLogic;
    TOKEN token;
    int token_value = 8;

    public void SetCardLogic(CARD_LOGIC logic) {
        this.cardLogic = logic;
    }

    public void SetToken(TOKEN token) {
        this.token = token;
    }

    public boolean check(BOOKSHELF bookshelf) {
        return this.cardLogic.CheckCardLogic(bookshelf);
    }
}
