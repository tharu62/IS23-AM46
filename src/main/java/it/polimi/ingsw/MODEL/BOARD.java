package it.polimi.ingsw.MODEL;

public class BOARD {
    public item[][] Grid = new item[9][9];
    BAG bag = new BAG();
    public int[] itemPos = new int[6];
    int itemCounter = 0;

    public void setGrid(int playerNumber){
        bag.setItemList();
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
                Grid[4][8]=item.OBJECT;
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
        this.Grid = Grid;
    }


    public item drawItem(int n, int m){
        item temp;
        if(cannotTakeItem(n,m)){
            return item.EMPTY;
        }
        if(this.IsToBeRestored()){
            restore();
        }
        temp = Grid[n][m];
        Grid[n][m] = item.OBJECT;
        return temp;

    }

    private void restore(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(Grid[i][j].equals(item.OBJECT)){
                    Grid[i][j]= bag.draw();
                }

            }
        }
    }

    private boolean cannotTakeItem(int n, int m) {
        if (Grid[n][m]==item.EMPTY || Grid[n][m]==item.OBJECT){
            return true;
        }
        if (itemCounter == 0 && count_neighbours(n, m) == 4) return true;
        if ((itemCounter == 2 || itemCounter == 4) && count_neighbours(n, m) == 3) return true;
        if (itemCounter == 0) {
            itemPos[itemCounter] = n;
            itemPos[itemCounter + 1] = m;
            itemCounter += 2;
            return false;
        }
        if (itemCounter == 2) {
            if (itemPos[itemCounter - 2] == n) {
                if (m > 0 && itemPos[itemCounter - 1] == m - 1 || m < 8 && itemPos[itemCounter - 1] == m + 1) {
                    itemPos[itemCounter] = n;
                    itemPos[itemCounter + 1] = m;
                    itemCounter += 2;
                    return false;
                }
            }
            if (itemPos[itemCounter - 1] == m) {
                if (n > 0 && itemPos[itemCounter - 2] == n - 1 || n < 8 && itemPos[itemCounter - 2] == n + 1) {
                    itemPos[itemCounter] = n;
                    itemPos[itemCounter + 1] = m;
                    itemCounter += 2;
                    return false;
                }
            }
        } else {
            if (itemPos[itemCounter - 2] == n) {
                if ((m > 0 && itemPos[itemCounter - 1] == m - 1 || m < 8 && itemPos[itemCounter - 1] == m + 1) || (m > 1 && itemPos[itemCounter - 1] == m - 2 || m < 7 && itemPos[itemCounter - 1] == m + 2)) {
                    itemPos[itemCounter] = n;
                    itemPos[itemCounter + 1] = m;
                    itemCounter = 0;
                    return false;
                }
            }
            if (itemPos[itemCounter - 1] == m) {
                if ((n > 0 && itemPos[itemCounter - 2] == n - 1 || n < 8 && itemPos[itemCounter - 2] == n + 1) || (n > 1 && itemPos[itemCounter - 2] == n - 2 || n < 7 && itemPos[itemCounter - 2] == n + 2)) {
                    itemPos[itemCounter] = n;
                    itemPos[itemCounter + 1] = m;
                    itemCounter = 0;
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method counts the number of tiles near a specific tile
     * @param row: the row where the tile is located
     * @param column: the column where the tile is located
     * @return the number of neighbours
     */
    private int count_neighbours(int row, int column) {
        int cont = 0;
        if (row > 0 && Grid[row - 1][column] != item.EMPTY && Grid[row - 1][column] != item.OBJECT) cont++;
        if (row < 8 && Grid[row + 1][column] != item.EMPTY && Grid[row + 1][column] != item.OBJECT) cont++;
        if (column > 0 && Grid[row][column - 1] != item.EMPTY && Grid[row][column - 1] != item.OBJECT) cont++;
        if (column < 8 && Grid[row][column + 1] != item.EMPTY && Grid[row][column + 1] != item.OBJECT) cont++;
        return cont;
    }

    private boolean IsToBeRestored(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(Grid[i][j] != item.EMPTY &&  Grid[i][j] != item.OBJECT && count_neighbours(i,j) > 0){
                    return false;
                }
            }
        }
        return true;
    }

}
