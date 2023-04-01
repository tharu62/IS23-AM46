package it.polimi.ingsw.MODEL;

public class BOARD {
    item[][] Grid= new item[9][9];
    BAG bag= new BAG();

    public void setGrid(int playerNumber){
        bag.setItemList();
        /** LOGICHE DI POSIZIONAMENTO DEGLI ITEM SULLA GRID**/
        item[][] Grid= { {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.EMPTY, item.OBJECT,item.OBJECT,item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.EMPTY, item.OBJECT,item.OBJECT,item.OBJECT,item.EMPTY, item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.EMPTY},
                         {item.EMPTY, item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.EMPTY},
                         {item.EMPTY, item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.EMPTY, item.OBJECT,item.OBJECT,item.OBJECT,item.EMPTY, item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.OBJECT,item.OBJECT,item.EMPTY, item.EMPTY, item.EMPTY},
                         {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY}
                        };

        if(playerNumber>2) {
            Grid[1][4] = item.OBJECT;
            Grid[3][3] = item.OBJECT;
            Grid[3][7] = item.OBJECT;
            Grid[4][9] = item.OBJECT;
            Grid[6][1] = item.OBJECT;
            Grid[7][3] = item.OBJECT;
            Grid[7][7] = item.OBJECT;
            Grid[9][6] = item.OBJECT;
        }

        if(playerNumber>3){
            Grid[1][5]=item.OBJECT;
            Grid[2][6]=item.OBJECT;
            Grid[4][2]=item.OBJECT;
            Grid[5][1]=item.OBJECT;
            Grid[5][9]=item.OBJECT;
            Grid[6][8]=item.OBJECT;
            Grid[8][4]=item.OBJECT;
            Grid[9][5]=item.OBJECT;
        }
    }


    public item drawItem(int n, int m){
        item temp;
        if(cannotTakeItem(n,m)){
            return item.EMPTY;
        }
        temp=Grid[n][m];
        Grid[n][m]=item.EMPTY;
        return temp;
    }

    private boolean cannotTakeItem(int n, int m){
        if(Grid[n][m]==item.EMPTY){
            return true;
        }
        /** logiche di controllo item adiacenti **/
        return false;
    }
}
