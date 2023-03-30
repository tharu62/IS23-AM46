package it.polimi.ingsw.MODEL;

public class BOARD extends BAG {
    item[][] Grid= new item[9][9];
    BAG bag= new BAG();

    public void setGrid(int playerNumber){
        bag.setItemList();
        /** LOGICHE DI POSIZIONAMENTO DEGLI ITEM SULLA GRID**/
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
