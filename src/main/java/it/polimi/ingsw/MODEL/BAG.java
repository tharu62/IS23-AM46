package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BAG {
    List<item> itemList= new ArrayList<item>(132);
    int CATS=0;
    int BOOKS=0;
    int GAMES=0;
    int FRAMES=0;
    int TROPHIES=0;
    int PLANTS=0;

    public void setItemList(){
        for(int j=0; j<133;j++) {
            Random rand = new Random();
            int upperbound = 7;
            int i = rand.nextInt(upperbound);
            int l=0;
            label:
            while(l==0){
            switch (i){
                case(1):{
                    if(this.CATS<23){
                        this.itemList.add(item.CATS);
                        l++;
                    }
                    else{
                        i++;
                        continue label;
                    }

                }
                case(2):{
                    if(this.GAMES<23){
                        this.itemList.add(item.GAMES);
                        l++;
                    }
                    else {
                        i++;
                        continue label;
                    }
                }
                case(3):{
                    if(this.BOOKS<23){
                        this.itemList.add(item.BOOKS);
                        l++;
                    }
                    else {
                        i++;
                        continue label;
                        }
                }
                case(4):{
                    if(this.FRAMES<23){
                        this.itemList.add(item.FRAMES);
                        l++;
                    }
                    else {
                        i++;
                        continue label;
                    }
                }
                case(5):{
                    if(this.TROPHIES<23){
                        this.itemList.add(item.TROPHIES);
                        l++;
                    }
                    else{
                        i++;
                        continue label;
                    }
                }
                case(6):{
                    if(this.PLANTS<23){
                        this.itemList.add(item.PLANTS);
                        l++;
                    }
                    else{
                        i=1;
                        continue label;
                    }
                }
            }
            }
        }
    }

    public item draw(){
        return itemList.get(itemList.size());
    }

}
