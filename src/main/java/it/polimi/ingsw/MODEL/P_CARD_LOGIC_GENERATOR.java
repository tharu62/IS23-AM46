package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class P_CARD_LOGIC_GENERATOR {
    P_CARD_LOGIC temp;
    Random rand = new Random();
    int upperbound = 12;
    List<Integer> alreadyTakenLogic = new ArrayList<>();
    public P_CARD_LOGIC SetCardLogic() {
        int i;
        do {
            i = rand.nextInt(upperbound);
        } while (alreadyTakenLogic.contains(i));
        alreadyTakenLogic.add(i);
        if (i==0) this.temp = new P_CARD_LOGIC_1();
        if (i==1) this.temp = new P_CARD_LOGIC_2();
        if (i==2) this.temp = new P_CARD_LOGIC_3();
        if (i==3) this.temp = new P_CARD_LOGIC_4();
        if (i==4) this.temp = new P_CARD_LOGIC_5();
        if (i==5) this.temp = new P_CARD_LOGIC_6();
        if (i==6) this.temp = new P_CARD_LOGIC_7();
        if (i==7) this.temp = new P_CARD_LOGIC_8();
        if (i==8) this.temp = new P_CARD_LOGIC_9();
        if (i==9) this.temp = new P_CARD_LOGIC_10();
        if (i==10) this.temp = new P_CARD_LOGIC_11();
        if (i==11) this.temp = new P_CARD_LOGIC_12();
        return temp;
    }
}
