package it.polimi.ingsw.MODEL;

import static it.polimi.ingsw.MODEL.P_CARD_LOGIC_1.calculateScore;

public interface P_CARD_LOGIC {
    /**
     * This method returns the points made by the player based on his personal goal.
     * It grants points if you match the highlighted spaces with the corresponding item tiles.
     * @param bookshelf: player's bookshelf
     * @return the points made by the player
     */
    int checkCardLogic(BOOKSHELF bookshelf);

    /**
     * This method returns the id of the card. Each card has a different objective and a different id.
     * @return id of the card. ( it is a pre-set value )
     */
    int getId();
}

class P_CARD_LOGIC_1 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[0][0].equals(item.PLANTS)) cont++;
        if (grid[0][2].equals(item.FRAMES)) cont++;
        if (grid[1][4].equals(item.CATS)) cont++;
        if (grid[2][3].equals(item.BOOKS)) cont++;
        if (grid[3][1].equals(item.GAMES)) cont++;
        if (grid[5][2].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    static int calculateScore(int cont) {
        if (cont == 1) return 1;
        if (cont == 2) return 2;
        if (cont == 3) return 4;
        if (cont == 4) return 6;
        if (cont == 5) return 9;
        if (cont == 6) return 12;
        return 0;
    }

    @Override
    public int getId() {
        return 1;
    }
}

class P_CARD_LOGIC_2 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[1][1].equals(item.PLANTS)) cont++;
        if (grid[5][4].equals(item.FRAMES)) cont++;
        if (grid[2][0].equals(item.CATS)) cont++;
        if (grid[3][4].equals(item.BOOKS)) cont++;
        if (grid[2][2].equals(item.GAMES)) cont++;
        if (grid[4][3].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 2;
    }
}

class P_CARD_LOGIC_3 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[2][2].equals(item.PLANTS)) cont++;
        if (grid[1][0].equals(item.FRAMES)) cont++;
        if (grid[3][1].equals(item.CATS)) cont++;
        if (grid[5][0].equals(item.BOOKS)) cont++;
        if (grid[1][3].equals(item.GAMES)) cont++;
        if (grid[3][4].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 3;
    }
}

class P_CARD_LOGIC_4 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[3][3].equals(item.PLANTS)) cont++;
        if (grid[2][2].equals(item.FRAMES)) cont++;
        if (grid[4][2].equals(item.CATS)) cont++;
        if (grid[4][1].equals(item.BOOKS)) cont++;
        if (grid[0][4].equals(item.GAMES)) cont++;
        if (grid[2][0].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 4;
    }
}

class P_CARD_LOGIC_5 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[4][4].equals(item.PLANTS)) cont++;
        if (grid[3][1].equals(item.FRAMES)) cont++;
        if (grid[5][3].equals(item.CATS)) cont++;
        if (grid[3][2].equals(item.BOOKS)) cont++;
        if (grid[5][0].equals(item.GAMES)) cont++;
        if (grid[1][1].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 5;
    }
}

class P_CARD_LOGIC_6 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[5][0].equals(item.PLANTS)) cont++;
        if (grid[4][3].equals(item.FRAMES)) cont++;
        if (grid[0][4].equals(item.CATS)) cont++;
        if (grid[2][3].equals(item.BOOKS)) cont++;
        if (grid[4][1].equals(item.GAMES)) cont++;
        if (grid[0][2].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 6;
    }
}

class P_CARD_LOGIC_7 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[2][1].equals(item.PLANTS)) cont++;
        if (grid[1][3].equals(item.FRAMES)) cont++;
        if (grid[0][0].equals(item.CATS)) cont++;
        if (grid[5][2].equals(item.BOOKS)) cont++;
        if (grid[4][4].equals(item.GAMES)) cont++;
        if (grid[3][0].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 7;
    }
}

class P_CARD_LOGIC_8 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[3][0].equals(item.PLANTS)) cont++;
        if (grid[0][4].equals(item.FRAMES)) cont++;
        if (grid[1][1].equals(item.CATS)) cont++;
        if (grid[4][3].equals(item.BOOKS)) cont++;
        if (grid[5][3].equals(item.GAMES)) cont++;
        if (grid[2][2].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 8;
    }
}

class P_CARD_LOGIC_9 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[4][4].equals(item.PLANTS)) cont++;
        if (grid[5][0].equals(item.FRAMES)) cont++;
        if (grid[2][2].equals(item.CATS)) cont++;
        if (grid[3][4].equals(item.BOOKS)) cont++;
        if (grid[0][2].equals(item.GAMES)) cont++;
        if (grid[4][1].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 9;
    }
}

class P_CARD_LOGIC_10 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[5][3].equals(item.PLANTS)) cont++;
        if (grid[4][1].equals(item.FRAMES)) cont++;
        if (grid[3][3].equals(item.CATS)) cont++;
        if (grid[2][0].equals(item.BOOKS)) cont++;
        if (grid[1][1].equals(item.GAMES)) cont++;
        if (grid[0][4].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 10;
    }
}

class P_CARD_LOGIC_11 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[0][2].equals(item.PLANTS)) cont++;
        if (grid[3][2].equals(item.FRAMES)) cont++;
        if (grid[4][4].equals(item.CATS)) cont++;
        if (grid[1][1].equals(item.BOOKS)) cont++;
        if (grid[2][0].equals(item.GAMES)) cont++;
        if (grid[5][3].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 11;
    }
}

class P_CARD_LOGIC_12 implements P_CARD_LOGIC{

    public int checkCardLogic(BOOKSHELF bookshelf) {
        int cont = 0;
        item[][] grid = bookshelf.getGrid();
        if (grid[1][1].equals(item.PLANTS)) cont++;
        if (grid[2][2].equals(item.FRAMES)) cont++;
        if (grid[5][0].equals(item.CATS)) cont++;
        if (grid[0][2].equals(item.BOOKS)) cont++;
        if (grid[4][4].equals(item.GAMES)) cont++;
        if (grid[3][3].equals(item.TROPHIES)) cont++;
        return calculateScore(cont);
    }

    @Override
    public int getId() {
        return 12;
    }
}

