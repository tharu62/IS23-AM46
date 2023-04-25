package it.polimi.ingsw.MODEL;
public abstract class PERSONAL_GOALS {
    P_CARD_LOGIC cardLogic;
    abstract void SetCardLogic(P_CARD_LOGIC logic);
    abstract int checkCardLogic(BOOKSHELF bookshelf);
}



