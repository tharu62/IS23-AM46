package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class BOOKSHELF {
    item[][] Grid = {{item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY}};
    boolean IsFull=false;
    public List<item> itemToPut= new ArrayList<>();
    int itemsInGrid=0;


    /**
     * This method places the items drawn in this turn in the bookshelf ( array item[][] ).
     *
     * @param m value of the column in which to place the items
     * @param a place order of the first item in the ItemToPut array
     * @param b place order of the second item in the ItemToPut array
     * @param c place order of the third item in the ItemToPut array
     * @return
     * If there is no space to place the items in the column, it returns false.
     * If there is space to place the items in the column, it increments the item counter, that counts
     * the number of items in the bookshelf, and deletes the drawn items saved in the array ItemToPut,
     * then it return true.
     */
    public boolean putItems(int m,int a, int b, int c){
        if(itemToPut.size() == 0){
            return false;
        }
        if( (firstFreeRow(m) < 0)  || firstFreeRow(m) < (itemToPut.size() - 1)){
            return false;
        }
        for(int j=0; j<3; j++){
            if (j==a && itemToPut.get(0) != null){
                Grid[firstFreeRow(m)][m] = itemToPut.get(0);
                this.itemsInGrid++;
            }
            if(j==b && itemToPut.size() > 1){
                Grid[firstFreeRow(m)][m] = itemToPut.get(1);
                this.itemsInGrid++;
            }
            if (j==c && itemToPut.size() > 2){
                Grid[firstFreeRow(m)][m] = itemToPut.get(2);
                this.itemsInGrid++;
            }
        }
        itemToPut.clear();
        if(itemsInGrid == 30){
            this.IsFull = true;
        }
        return true;
    }

    /**
     * This method return the score of the adjacent items of type <comp> by searching the number of items touching each
     * other with the method dfs(), and then it applies the game logic to return the real score.
     *
     * @param comp is the type of item to check.
     * @return the score for that type of item.
     */
    public  int checkAdjacentItem(item comp){
        int score=0;
        int temp;
        for(int i=0; i<Grid.length ; i++){
            for(int j=0; j<Grid[j].length ;j++){
                if(Grid[i][j]==comp){
                    temp = dfs(i,j,comp);
                    if (temp == 3) score += 2;
                    if (temp == 4) score += 3;
                    if (temp == 5) score += 5;
                    if (temp > 5) score += 8;
                }
            }
        }
        return score;
    }

    /**
     * This method is inspired by the DEPTH FIRST SEARCH (DFS) Algorithm.
     * It searches for all the possible items that touches each other on the sides and return the number of these items.
     * THE ITEM CHECKED ARE SET TO EMPTY, this means that once the bookshelf has been checked for a specific type of
     * item, it can't be checked again.
     *
     * @param i row of the starting item
     * @param j column of the starting item
     * @param comp is the item type of the item to check.
     * @return
     */
    private int dfs(int i, int j, item comp){
        int score=0;
        if(j < 4 && Grid[i][j+1]==comp){
            Grid[i][j+1]=item.EMPTY;
            score+= 1+dfs(i,j+1,comp);
        }
        if(i < 5 && Grid[i+1][j]==comp){
            Grid[i+1][j]=item.EMPTY;
            score+= 1+dfs(i+1,j,comp);
        }
        if(j > 0 && Grid[i][j-1]==comp){
            Grid[i][j-1]=item.EMPTY;
            score+= 1+dfs(i,j-1,comp);
        }
        if(i > 0 && Grid[i-1][j]==comp){
            Grid[i-1][j]=item.EMPTY;
            score+= 1+dfs(i-1,j,comp);
        }
        Grid[i][j]=item.EMPTY;
        return score;
    }

    /**
     * This method return the attribute Grid of this class.
     * @return Grid attribute.
     */
    public item[][] getGrid() {
        return this.Grid;
    }

    /**
     * This method checks the attribute Grid of this class in the column with index 'x'.
     * @param x is the index of the column to check.
     * @return the index of the first row of the checked column that is not occupied with an item.
     *         The column is checked from the bottom (row index 5) to the top (row index 0).
     */
    public int firstFreeRow(int x) {
        for(int i = 5; i >=0; i--){
            if(this.Grid[i][x].equals(item.EMPTY)){
                return i;
            }
        }
        return -1;
    }
}
