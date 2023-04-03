package it.polimi.ingsw.MODEL;

import java.util.Random;

public class P_CARD_LOGIC_GENERATOR {
    P_CARD_LOGIC temp;
    Random rand = new Random();
    int upperbound = 12;
    int i = rand.nextInt(upperbound);
    public P_CARD_LOGIC SetCardLogic() {
        if (i==1) {
            this.temp = new P_CARD_LOGIC_1();
            return temp;
        }
        if (i==2) {
            this.temp = new P_CARD_LOGIC_2();
            return temp;
        }
        if (i==3) {
            this.temp = new P_CARD_LOGIC_3();
            return temp;
        }
        if (i==4) {
            this.temp = new P_CARD_LOGIC_4();
            return temp;
        }
        if (i==5) {
            this.temp = new P_CARD_LOGIC_5();
            return temp;
        }
        if (i==6) {
            this.temp = new P_CARD_LOGIC_6();
            return temp;
        }
        if (i==7) {
            this.temp = new P_CARD_LOGIC_7();
            return temp;
        }
        if (i==8) {
            this.temp = new P_CARD_LOGIC_8();
            return temp;
        }
        if (i==9) {
            this.temp = new P_CARD_LOGIC_9();
            return temp;
        }
        if (i==10) {
            this.temp = new P_CARD_LOGIC_10();
            return temp;
         }
        if (i==11) {
            this.temp = new P_CARD_LOGIC_11();
            return temp;
         }
        if (i==12) {
            this.temp = new P_CARD_LOGIC_12();
            return temp;
        }

        return new P_CARD_LOGIC_12();
    }
}
