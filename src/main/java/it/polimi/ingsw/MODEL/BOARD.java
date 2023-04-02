package it.polimi.ingsw.MODEL;

public class BOARD {
    item[][] Grid= new item[9][9];
    BAG bag= new BAG();
    int[] tilePos= new int[6];
    int tileCounter=0;

    public void setGrid(int playerNumber){
        bag.setItemList();
        /** logic to position items on grid on first turn **/
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

        if(playerNumber>=3){
            Grid[0][3]=item.OBJECT;
            Grid[2][2]=item.OBJECT;
            Grid[2][6]=item.OBJECT;
            Grid[3][8]=item.OBJECT;
            Grid[5][0]=item.OBJECT;
            Grid[6][2]=item.OBJECT;
            Grid[6][6]=item.OBJECT;
            Grid[8][5]=item.OBJECT;
            if(playerNumber==4){
                Grid[0][4]=item.OBJECT;
                Grid[1][5]=item.OBJECT;
                Grid[3][1]=item.OBJECT;
                Grid[4][0]=item.OBJECT;
                Grid[4][7]=item.OBJECT;
                Grid[5][7]=item.OBJECT;
                Grid[7][3]=item.OBJECT;
                Grid[8][4]=item.OBJECT;
            }
        }


        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(Grid[i][j]==item.OBJECT){
                    Grid[i][j]=bag.draw();
                }
            }
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

    private boolean cannotTakeItem(int n, int m) {
        if (Grid[n][m] == item.EMPTY) {
            return true;
        }
        /** logics to control adjacent item tiles **/
        if(Grid[n][m-1]==item.EMPTY || Grid[n][m+1]==item.EMPTY || Grid[n-1][m]==item.EMPTY || Grid[n+1][m]==item.EMPTY){
            if(Grid[n-1][m-1]==item.EMPTY || Grid[n+1][m+1]==item.EMPTY || Grid[n-1][m+1]==item.EMPTY || Grid[n+1][m-1]==item.EMPTY){
                tilePos[tileCounter]=n;
                tilePos[tileCounter+1]=m;
                tileCounter+=2;
                return false;
            }

            if(tileCounter>=2){                                       /** control if there is an item already picked in the edges of the item picked now **/
                if((tilePos[tileCounter-1]==(n-1) && tilePos[tileCounter]==(m-1)) || (tilePos[tileCounter-2]==(n-1) && tilePos[tileCounter-1]==(m-1))){
                    return true;
                }
                if((tilePos[tileCounter-1]==(n+1) && tilePos[tileCounter]==(m+1)) || (tilePos[tileCounter-2]==(n+1) && tilePos[tileCounter+1]==(m-1))){
                    return true;
                }
                if((tilePos[tileCounter-1]==(n-1) && tilePos[tileCounter]==(m+1)) || (tilePos[tileCounter-2]==(n-1) && tilePos[tileCounter-1]==(m+1))){
                    return true;
                }
                if((tilePos[tileCounter-1]==(n+1) && tilePos[tileCounter]==(m-1)) || (tilePos[tileCounter-2]==(n+1) && tilePos[tileCounter-1]==(m-1))){
                    return true;
                }
            }

            tilePos[tileCounter]=n;
            tilePos[tileCounter+1]=m;
            tileCounter+=2;
            return false;
        }
        return true;

    }
}
