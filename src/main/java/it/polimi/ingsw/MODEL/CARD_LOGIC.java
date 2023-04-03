package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CARD_LOGIC {
    boolean CheckCardLogic(BOOKSHELF bookshelf);
}

class CARD_LOGIC_1 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are eight tiles of the same type.
     * There’s no restriction about the position of these tiles
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf ) {
        int n_cats = 0, n_books = 0, n_frames = 0, n_trophies = 0, n_games = 0, n_plants = 0;
        item tile;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = bookshelf.getGrid()[i][j];
                if (!tile.equals(item.EMPTY)) {
                    if (tile == item.CATS) n_cats++;
                    if (tile == item.BOOKS) n_books++;
                    if (tile == item.FRAMES) n_frames++;
                    if (tile == item.TROPHIES) n_trophies++;
                    if (tile == item.GAMES) n_games++;
                    if (tile == item.PLANTS) n_plants++;
                }
            }
        }
        return n_cats == 8 || n_books == 8 || n_frames == 8 || n_trophies == 8 || n_games == 8 || n_plants == 8;
    }
}

class CARD_LOGIC_2 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are five tiles of the same type forming an X.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        item tile;
        item[][] grid = bookshelf.getGrid();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                tile = grid[i][j];
                if (!tile.equals(item.EMPTY)) {
                    if (tile.equals(grid[i + 2][j]) &&
                            tile.equals(grid[i + 1][j + 1]) &&
                            tile.equals(grid[i][j + 2]) &&
                            tile.equals(grid[i + 2][j + 2]))
                        return true;
                }
            }
        }
        return false;
    }
}

class CARD_LOGIC_3 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are
     * four tiles of the same type in the four corners of the bookshelf
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        item[][] grid = bookshelf.getGrid();
        return grid[0][0].equals(grid[4][0]) &&
                grid[0][0].equals(grid[0][5]) &&
                grid[0][0].equals(grid[4][5]);

    }
}

class CARD_LOGIC_4 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are
     * five tiles of the same type forming a diagonal
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int[] cont = {0, 0, 0, 0};
        item[][] grid = bookshelf.getGrid();
        for (int i = 1; i < 5; i++) {
            if(!grid[0][0].equals(item.EMPTY) && grid[i][i].equals(grid[0][0])) cont[0]++;
            if(!grid[4][0].equals(item.EMPTY) && grid[4 - i][i].equals(grid[4][0])) cont[1]++;
            if(!grid[1][0].equals(item.EMPTY) && grid[i + 1][i].equals(grid[1][0])) cont[2]++;
            if(!grid[5][0].equals(item.EMPTY) && grid[5 - i][i].equals(grid[5][0])) cont[3]++;
        }
        for (int i = 0; i < 4; i++) {
            if (cont[i] == 4) return true;
        }
        return false;

    }
}

class CARD_LOGIC_5 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are
     * two columns each formed by 6 different types of tiles
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<item>();
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        for (int i = 0; i < 5; i++) {
            if (!grid[0][i].equals(item.EMPTY)) {
                for (int j = 0; j < 6; j++) {
                    set.add(grid[j][i]);
                }
                if (set.size() == 6) cont++;
                set.clear();
            }
        }
        return cont == 2;

    }
}

class CARD_LOGIC_6 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are
     * three columns each formed by 6 tiles of maximum three different types.
     * One column can show the same or a different combination of another column
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<item>();
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        for (int i = 0; i < 5; i++) {
            if (!grid[0][i].equals(item.EMPTY)) {
                for (int j = 0; j < 6; j++) {
                    set.add(grid[j][i]);
                }
                if (set.size() <= 3) cont++;
                set.clear();
            }
        }
        return cont == 3;

    }
}

class CARD_LOGIC_7 implements CARD_LOGIC{
    /**
     * This method returns true if inside the player's bookshelf there are
     * two lines each formed by 5 different types of tiles.
     * One line can show the same or a different combination of the other line.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<item>();
        int cont = 0;
        item tile;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = bookshelf.getGrid()[i][j];
                if (tile.equals(item.EMPTY)) break;
                set.add(tile);
            }
            if (set.size() == 5) cont++;
            set.clear();
        }
        return cont == 2;
    }
}

class CARD_LOGIC_8 implements CARD_LOGIC {
    /**
     * This method returns true if inside the player's bookshelf there are
     * four lines each formed by 5 tiles of maximum three different types.
     * One line can show the same or a different combination of another line.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<item>();
        int cont = 0, cont_item = 0;
        item tile;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = bookshelf.getGrid()[i][j];
                if (!tile.equals(item.EMPTY)) {
                    set.add(tile);
                    cont_item++;
                }
            }
            if (set.size() <= 3 && cont_item == 5) cont++;
            set.clear();
        }
        return cont == 4;
    }
}

class CARD_LOGIC_9 implements CARD_LOGIC {
    /**
     * This method returns true if inside the player's bookshelf there are
     * two groups each containing 4 tiles of the same type in a 2x2 square.
     * The tiles of one square can be different from those of the other square
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] table = bookshelf.getGrid();
        item tile, tileLeft, tileRight, tileUp, tileDown;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                tile = table[i][j];
                if(j == 0) tileLeft = item.EMPTY;
                else tileLeft = table [i][j - 1];
                tileRight = table [i][j + 1];
                if (i == 0) tileUp = item.EMPTY;
                else tileUp = table [i - 1][j];
                tileDown = table [i + 1][j];
                if (!tile.equals(item.EMPTY)) {
                    if (tile.equals(tileRight) && tile.equals(tileDown) && tile.equals(table[i + 1][j + 1]))
                        if (!tile.equals(tileUp) && !tile.equals(tileLeft) &&
                            !tile.equals(table[i - 1][j + 1]) && !tile.equals(table[i][j + 2]) &&
                            !tile.equals(table[i + 1][j - 1]) && !tile.equals(table[i + 2][j]) &&
                            !tile.equals(table[i + 2][j + 1]) && !tile.equals(table[i + 1][j + 2])) cont++;
                }
            }
        }
        return cont == 2;
    }
}

class CARD_LOGIC_10 implements CARD_LOGIC {
    /**
     * This method return true if inside the player's bookshelf there are
     * five columns of increasing or decreasing height. Starting from the first column on the left or on the right,
     * each next column must be made of exactly one more tile. Tiles can be of any type.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        item[][] grid = bookshelf.getGrid();
        int[] cont = {0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            if (grid[i][i].equals(item.EMPTY) && !grid[i + 1][i].equals(item.EMPTY)) cont[0]++;
            if (grid[i][4 - i].equals(item.EMPTY) && !grid[i + 1][4 - i].equals(item.EMPTY)) cont[1]++;
            if (i > 0) {
                if (!grid[i][i].equals(item.EMPTY) && grid[i - 1][i].equals(item.EMPTY)) cont[2]++;
                if (!grid[i][4 - i].equals(item.EMPTY) && grid[i - 1][4 - i].equals(item.EMPTY)) cont[3]++;
            }
        }
        if (!grid[0][0].equals(item.EMPTY)) cont[2]++;
        if (!grid[0][4].equals(item.EMPTY)) cont[3]++;
        for (int i = 0; i < 4; i++) {
            if (cont[i] == 5) return true;
        }
        return false;
    }
}

class CARD_LOGIC_11 implements CARD_LOGIC {
    /**
     * This method return true if inside the player's bookshelf there are
     * six groups each containing at least 2 tiles of the same type.
     * The tiles of one group can be different from those of another group
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        List<item> itemToCheck = new ArrayList<item>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                item tile = grid[i][j];
                if (!tile.equals(item.EMPTY) && !itemToCheck.contains(tile)) {
                    if ((tile.equals(grid[i][j + 1]) || tile.equals(grid[i + 1][j])) && j < 4 && i < 5) {
                        cont++;
                        if (tile.equals(grid[i][j + 1])) itemToCheck.add(grid[i][j + 1]);
                        if (tile.equals(grid[i + 1][j])) itemToCheck.add(grid[i + 1][j]);
                        if (tile.equals(grid[i][j - 1])) itemToCheck.add(grid[i][j - 1]);
                    }
                    if (j == 4 && i < 5)
                        if (tile.equals(grid[i + 1][j])) {
                            cont++;
                            itemToCheck.add(grid[i + 1][j]);
                        }
                    if (i == 5 && j < 4)
                        if (tile.equals(grid[i][j + 1])){
                            cont++;
                            itemToCheck.add(grid[i][j + 1]);
                        }
                } else if (itemToCheck.contains(tile)) {
                    if (tile.equals(grid[i][j + 1])) itemToCheck.add(grid[i][j + 1]);
                    if (tile.equals(grid[i + 1][j])) itemToCheck.add(grid[i + 1][j]);
                    itemToCheck.remove(tile);
                }
            }
        }
        return cont == 6;
    }
}

class CARD_LOGIC_12 implements CARD_LOGIC {
    /**
     * This method return true if inside the player's bookshelf there are
     * four groups each containing at least 4 tiles of the same type.
     * The tiles of one group can be different from those of another group.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0, cont_item = 0;
        item[][] grid = bookshelf.getGrid();
        item tile;
        List<item> itemToCheck = new ArrayList<item>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = grid[i][j];
                if (!tile.equals(item.EMPTY) && !itemToCheck.contains(tile)) {
                    cont_item = countNeighboursType(grid, i, j, null) + 1;
                    if (j < 4 && tile.equals(grid[i][j + 1])) itemToCheck.add(grid[i][j + 1]);
                    if (i < 5 && tile.equals(grid[i + 1][j])) itemToCheck.add(grid[i + 1][j]);
                    if (j > 0 && tile.equals(grid[i][j - 1])) itemToCheck.add(grid[i][j - 1]);
                } else if (itemToCheck.contains(tile)) {
                    if (j < 4 && tile.equals(grid[i][j + 1])) itemToCheck.add(grid[i][j + 1]);
                    if (i < 5 && tile.equals(grid[i + 1][j])) itemToCheck.add(grid[i + 1][j]);
                    itemToCheck.remove(tile);
                }
                if (cont_item > 3) cont++;
            }
        }
        return cont == 4;
    }

    /**
     * This method count how many tiles of the same type are nearby
     * @param table: it's the bookshelf
     * @param row: the row where the tile is located
     * @param column: the column where the tile is located
     * @param list: a list containing the tiles that have already been checked
     * @return the number of tiles of the same type that are nearby
     */
    private int countNeighboursType(item[][] table, int row, int column, List<item> list) {
        item tile = table[row][column];
        int cont = 0;
        list.add(tile);
        if (column < 4 && tile.equals(table[row][column + 1]) && !list.contains(table[row][column + 1])) {
            cont = 1 + countNeighboursType(table, row, column + 1, list);
        }
        if (row < 5 && tile.equals(table[row + 1][column]) && !list.contains(table[row + 1][column])) {
            cont = 1 + countNeighboursType(table, row + 1, column, list);
        }
        if (column > 0 && tile.equals(table[row][column - 1]) && !list.contains(table[row][column - 1])) {
            cont = 1 + countNeighboursType(table, row, column - 1, list);
        }
        return cont;
    }
}