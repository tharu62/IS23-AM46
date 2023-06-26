package it.polimi.ingsw.MODEL;

public class CONTROL_SELECTION {
    BOARD board;
    public int[] itemPos = new int[6];
    int itemCounter = 0;
    public boolean item( int n, int m){
        if (board.Grid[n][m]==item.EMPTY || board.Grid[n][m]==item.OBJECT){
            return true;
        }
        if (board.itemCounter == 0 && board.count_neighbours(n, m) == 4) return true;
        if ((itemCounter == 2 || itemCounter == 4) && board.count_neighbours(n, m) == 3) return true;
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
}
