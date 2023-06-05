package it.polimi.ingsw.MODEL;

import java.io.Serializable;

public class COMMON_GOAL_CARD extends COMMON_GOALS implements Serializable {
    CARD_LOGIC cardLogic;
    TOKEN token;
    int token_value = 8;

    public void SetCardLogic(CARD_LOGIC logic) {
        this.cardLogic = logic;
    }

    public CARD_LOGIC getCardLogic() {
        return cardLogic;
    }

    public void SetToken(TOKEN token) {
        this.token = token;
    }

    public boolean check(BOOKSHELF bookshelf) {
        return this.cardLogic.CheckCardLogic(bookshelf);
    }

    public int getToken_value() {
        return token_value;
    }
}
