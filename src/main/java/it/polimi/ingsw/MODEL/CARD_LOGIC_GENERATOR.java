package it.polimi.ingsw.MODEL;

import java.util.Random;

public class CARD_LOGIC_GENERATOR {
    CARD_LOGIC temp;
    Random rand = new Random();
    int upperbound = 25;
    int i = rand.nextInt(upperbound);
    public CARD_LOGIC SetCardLogic() {
        if (i < 10) {
            this.temp = new CARD_LOGIC_1();
            return temp;
        }
        if (i > 10) {
            this.temp = new CARD_LOGIC_2();
            return temp;
        }
        return new CARD_LOGIC_1();
    }
}
