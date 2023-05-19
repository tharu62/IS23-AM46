package it.polimi.ingsw.MODEL;

public abstract class COMMON_GOALS {
    CARD_LOGIC l;
    TOKEN t;
    int t_v;
    abstract void SetCardLogic(CARD_LOGIC logic);
    abstract void SetToken(TOKEN token);
    abstract boolean check(BOOKSHELF bookshelf);

}

