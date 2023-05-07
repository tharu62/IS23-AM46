package it.polimi.ingsw.MODEL;

import java.util.Random;

public class CARD_LOGIC_GENERATOR {
    CARD_LOGIC temp;
    Random rand = new Random();
    //int upperbound = 12;
    int upperbound = 2; //used for testing
    int j = -1;
    public CARD_LOGIC SetCardLogic() {
        int i;
        do {
            i = rand.nextInt(upperbound);
        } while (j == i);
        j = i;
        if (i == 0) this.temp = new CARD_LOGIC_1();
        if (i == 1) this.temp = new CARD_LOGIC_2();
        if (i == 2) this.temp = new CARD_LOGIC_3();
        if (i == 3) this.temp = new CARD_LOGIC_4();
        if (i == 4) this.temp = new CARD_LOGIC_5();
        if (i == 5) this.temp = new CARD_LOGIC_6();
        if (i == 6) this.temp = new CARD_LOGIC_7();
        if (i == 7) this.temp = new CARD_LOGIC_8();
        if (i == 8) this.temp = new CARD_LOGIC_9();
        if (i == 9) this.temp = new CARD_LOGIC_10();
        if (i == 10) this.temp = new CARD_LOGIC_11();
        if (i == 11) this.temp = new CARD_LOGIC_12();
        return temp;
    }
}
