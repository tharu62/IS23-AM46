package it.polimi.ingsw.MODEL;

public interface CARD_LOGIC {
    public boolean CheckCardLogic(BOOKSHELF bookshelf);

}

class CARD_LOGIC_1 implements CARD_LOGIC{

    public boolean CheckCardLogic(BOOKSHELF bookshelf ) {
        int token=0;
        if(bookshelf.Grid[1][1]==item.CATS){
            token++;

        }
        return true;
    }
}

class CARD_LOGIC_2 implements CARD_LOGIC{
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {

        return false;
    }

}