package it.polimi.ingsw.MODEL;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public interface CARD_LOGIC {
    boolean CheckCardLogic(BOOKSHELF bookshelf);
    int getId();
}

class CARD_LOGIC_9 implements CARD_LOGIC, Serializable{
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

    @Override
    public int getId() {
        return 1;
    }

}

class CARD_LOGIC_10 implements CARD_LOGIC, Serializable{
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

    public int getId() {
        return 2;
    }
}

class CARD_LOGIC_8 implements CARD_LOGIC, Serializable{
    /**
     * This method returns true if inside the player's bookshelf there are
     * four tiles of the same type in the four corners of the bookshelf
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        item[][] grid = bookshelf.getGrid();
        if (grid[0][0].equals(item.EMPTY)) return false;
        return grid[0][0].equals(grid[0][4]) &&
                grid[0][0].equals(grid[5][0]) &&
                grid[0][0].equals(grid[5][4]);

    }

    public int getId() {
        return 3;
    }
}

class CARD_LOGIC_11 implements CARD_LOGIC, Serializable{
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

    public int getId() {
        return 4;
    }
}

class CARD_LOGIC_2 implements CARD_LOGIC, Serializable{
    /**
     * This method returns true if inside the player's bookshelf there are
     * two columns each formed by 6 different types of tiles
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<>();
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

    public int getId() {
        return 5;
    }
}

class CARD_LOGIC_5 implements CARD_LOGIC, Serializable{
    /**
     * This method returns true if inside the player's bookshelf there are
     * three columns each formed by 6 tiles of maximum three different types.
     * One column can show the same or a different combination of another column
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<>();
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

    public int getId() {
        return 6;
    }
}

class CARD_LOGIC_6 implements CARD_LOGIC, Serializable{
    /**
     * This method returns true if inside the player's bookshelf there are
     * two lines each formed by 5 different types of tiles.
     * One line can show the same or a different combination of the other line.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<>();
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

    public int getId() {
        return 7;
    }
}

class CARD_LOGIC_7 implements CARD_LOGIC, Serializable {
    /**
     * This method returns true if inside the player's bookshelf there are
     * four lines each formed by 5 tiles of maximum three different types.
     * One line can show the same or a different combination of another line.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        Set<item> set = new HashSet<>();
        int cont = 0, cont_item;
        item tile;
        for (int i = 0; i < 6; i++) {
            cont_item = 0;
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

    public int getId() {
        return 8;
    }
}

class CARD_LOGIC_1 implements CARD_LOGIC, Serializable {
    /**
     * This method returns true if inside the player's bookshelf there are
     * two groups each containing 4 tiles of the same type in a 2x2 square.
     * The tiles of one square can be different from those of the other square
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] table = bookshelf.getGrid();
        item tile, tileRight, tileDown;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                tile = table[i][j];
                tileRight = table [i][j + 1];
                tileDown = table [i + 1][j];
                if (!tile.equals(item.EMPTY)) {
                    if (tile == tileRight && tile == tileDown && tile == table[i + 1][j + 1]) {
                        if (checkBorders(table, i, j)) cont++;
                    }
                }
            }
        }
        return cont == 2;
    }

    public int getId() {
        return 9;
    }

    /**
     * This method checks if a 2x2 square made by items of the same type is surrounded by items of different types
     * @param grid: bookshelf's grid
     * @param row: the row where the tile is located
     * @param column: the column where the tile is located
     * @return true if the square is isolated
     */
    private boolean checkBorders(item[][] grid, int row, int column) {
        item tile = grid[row][column];
        if (column > 0)
            if (tile.equals(grid[row][column - 1]) || tile.equals(grid[row + 1][column - 1])) return false;
        if (column < 3)
            if (tile.equals(grid[row][column + 2]) || tile.equals(grid[row + 1][column + 2])) return false;
        if (row > 0)
            if (tile.equals(grid[row - 1][column]) || tile.equals(grid[row - 1][column + 1])) return false;
        if (row < 4)
            return !tile.equals(grid[row + 2][column]) && !tile.equals(grid[row + 2][column + 1]);
        return true;
    }
}

class CARD_LOGIC_12 implements CARD_LOGIC, Serializable {
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

    public int getId() {
        return 10;
    }
}

class CARD_LOGIC_4 implements CARD_LOGIC, Serializable {
    /**
     * This method return true if inside the player's bookshelf there are
     * six groups each containing at least 2 tiles of the same type.
     * The tiles of one group can be different from those of another group
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0, a;
        item[][] grid = bookshelf.getGrid();
        item tile;
        Set<Integer> itemToCheck = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = grid[i][j];
                if (!tile.equals(item.EMPTY)) {
                    a = calculateUniqueValue(i, j);
                    if (!itemToCheck.contains(a)) {
                        Set<Integer> curr_item = new HashSet<>();
                        curr_item.add(a);
                        Set<Integer> itemSameType = checkGroupsSameType(grid, i, j, curr_item);
                        if (itemSameType.size() > 1) cont++;
                        itemToCheck.addAll(itemSameType);
                    } else {
                        itemToCheck.remove(a);
                    }
                }
            }
        }
        return cont == 6;
    }

    public int getId() {
        return 11;
    }

    /**
     * This method calculates a number that is unique for each row-column pair
     * @param row: tile's row in the bookshelf
     * @param column: tile's column in the bookshelf
     * @return a number that is a linear combination of row and column
     */
    int calculateUniqueValue(int row, int column) {
        return 10 * row + column;
    }

    /**
     * This method return the tiles that belong to the same group
     * @param table: bookshelf's grid
     * @param row: tile's row in the bookshelf
     * @param column: tile's column in the bookshelf
     * @param listToCheck: list of item of the same in type in the same group
     * @return a Set of the unique value of the tiles that belong to the same group
     */
    Set<Integer> checkGroupsSameType(item[][] table, int row, int column, Set<Integer> listToCheck) {
        item tile = table[row][column];
        if (column < 4 && tile.equals(table[row][column + 1]) && !listToCheck.contains(calculateUniqueValue(row, column + 1))) {
            listToCheck.add(calculateUniqueValue(row, column + 1));
            listToCheck.addAll(checkGroupsSameType(table, row, column + 1, listToCheck));
        }
        if (row < 5 && tile.equals(table[row + 1][column]) && !listToCheck.contains(calculateUniqueValue(row + 1, column))) {
            listToCheck.add(calculateUniqueValue(row + 1, column));
            listToCheck.addAll(checkGroupsSameType(table, row + 1, column, listToCheck));
        }
        if (column > 0 && tile.equals(table[row][column - 1]) && !listToCheck.contains(calculateUniqueValue(row, column - 1))) {
            listToCheck.add(calculateUniqueValue(row, column - 1));
            listToCheck.addAll(checkGroupsSameType(table, row, column - 1, listToCheck));
        }
        if (row > 0 && tile.equals(table[row - 1][column]) && !listToCheck.contains(calculateUniqueValue(row - 1, column))) {
            listToCheck.add(calculateUniqueValue(row - 1, column));
            listToCheck.addAll(checkGroupsSameType(table, row - 1, column, listToCheck));
        }
        return listToCheck;
    }
}

class CARD_LOGIC_3 extends CARD_LOGIC_4  implements CARD_LOGIC, Serializable{
    /**
     * This method return true if inside the player's bookshelf there are
     * four groups each containing at least 4 tiles of the same type.
     * The tiles of one group can be different from those of another group.
     * @param bookshelf: player's bookshelf
     */
    public boolean CheckCardLogic(BOOKSHELF bookshelf) {
        int cont = 0, a;
        item[][] grid = bookshelf.getGrid();
        item tile;
        Set<Integer> itemToCheck = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tile = grid[i][j];
                if (!tile.equals(item.EMPTY)) {
                    a = calculateUniqueValue(i, j);
                    if (!itemToCheck.contains(a)) {
                        Set<Integer> curr_item = new HashSet<>();
                        curr_item.add(a);
                        Set<Integer> itemSameType = checkGroupsSameType(grid, i, j, curr_item);
                        if (itemSameType.size() > 3) cont++;
                        itemToCheck.addAll(itemSameType);
                    } else {
                        itemToCheck.remove(a);
                    }
                }
            }
        }
        return cont == 4;
    }

    public int getId() {
        return 12;
    }
}