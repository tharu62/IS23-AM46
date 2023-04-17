package it.polimi.ingsw.MODEL;
public abstract class PERSONAL_GOALS {
    P_CARD_LOGIC cardLogic;
    abstract void SetCardLogic(P_CARD_LOGIC logic);
    abstract int checkCardLogic(BOOKSHELF bookshelf);
}



class PERSONAL_GOAL_CARD extends PERSONAL_GOALS{
    P_CARD_LOGIC cardLogic;
    public void SetCardLogic(P_CARD_LOGIC logic){
        this.cardLogic= logic;
    }
    public int checkCardLogic(BOOKSHELF bookshelf){
        return this.cardLogic.checkCardLogic(bookshelf);
    }

}
