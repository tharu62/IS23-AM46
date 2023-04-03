package it.polimi.ingsw.MODEL;

public interface TOKEN {
    int UpdateValue(int token_value);
}


class TWO_PLAYERS_LOGIC implements TOKEN{

    public int UpdateValue(int token_value){
        if(token_value>4){return -4;}
        else{return 0;}
    }
}

class THREE_PLAYERS_LOGIC implements TOKEN{

    public int UpdateValue(int token_value){
        if(token_value>4){return -2;}
        else{return 0;}
    }
}

class FOUR_PLAYERS_LOGIC implements TOKEN{

    public int UpdateValue(int token_value){
        if(token_value>2){return -2;}
        else{return 0;}
    }
}
