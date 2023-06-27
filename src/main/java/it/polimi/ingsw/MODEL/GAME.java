package it.polimi.ingsw.MODEL;

public class GAME {
    public MASTER master = new MASTER();
    public SPACE space = new SPACE();
    public CHAT chat = new CHAT();
    public boolean IsOver = false;
    public int CurrentLobbySize = 0;
    public int LobbySize = 2;
    public String playerToPlay;
    P_CARD_LOGIC_GENERATOR generator = new P_CARD_LOGIC_GENERATOR();

    public void addPlayer(String username){
        PLAYER player = new PLAYER();
        player.username = username;
        space.player.add(this.CurrentLobbySize, player);
        master.player = space.player;
        this.CurrentLobbySize++;
    }

    public void setBoard(){
        space.setBoard(this.LobbySize);
    }

    public void DrawCommonGoalCards(){
        master.setFirstDraw(this.LobbySize);
    }

    public void DrawPersonalGoalCards(){
        for(int i=0; i<this.LobbySize; i++){
            space.player.get(i).drawPersonalGoalCard(generator.SetCardLogic());
        }
    }

    public void ChooseFirstPlayerSeat(){
        master.ChooseFirstPlayerSeat();
        this.playerToPlay = master.FirstPlayerSeat.username;
    }

    /**
     * The player starts his turn, if it's not the last, then round and turn are updated
     * and the player's bookshelves is checked.
     * If it's the last turn the score of each player is calculated checking personal goals and adjacent item_tiles
     * on the bookshelves, then the scores are compared and the winner's name is saved in Space.winner .
     * The score from the common goals cannot be checked here. below there is the reason.
     */
    public void masterStartTurn() {
        if (master.checkIfLastTurn(space.player.get(search(this.playerToPlay)).bookshelf)) {
            space.calculateScore();
            IsOver = true;
        }
    }

    /**
     * The player has to ask the MODEL if he can pick an item_tile. One item_tile at a time.
     * Once he is satisfied with the pick, the picked items are stored in his bookshelf class until he wants to put them in the bookshelf grid.
     *
     * @param n it's the row from witch to pick the item
     * @param m it's the column from witch to pick the item
     * @return  true only if the pick is valid
     */
    public boolean playerDrawItem(String username, int n, int m){
        if (this.playerToPlay.equals(username)){
            if (space.player.get(search(username)).bookshelf.IsFull) {
                return false;
            }
            return space.draw(search(username), n, m);
        }
        return false;
    }

    /**
     * The player chooses the order to put the items in the bookshelf by giving each item_tile a number that goes from 0 to 2.
     * for example:
     * if a = 2 then the first item picked is the last to be put in the bookshelf in the m column.
     * If you fill the bookshelf you update the game to it's last round, NOT necessarily the last turn.
     *
     * @param m it's the column to put the items
     * @param a it's the order of the first item to put in the bookshelf
     * @param b it's the order of the second item to put in the bookshelf
     * @param c it's the order of the third item to put in the bookshelf
     */
    public boolean playerPutItems(String username, int m,int a,int b, int c){
        if (this.playerToPlay.equals(username)) {
            return space.placeItem(search(username), m, a, b, c);
        }
        return false;
    }

    /**
     *
     * @param username
     */
    public void CheckScore(String username){
        if (this.playerToPlay.equals(username)) {
            int i = search(playerToPlay);
            int temp;
            if (!space.player.get(i).goalReached[0]) {
                temp = master.CheckCommonGoal(space.player.get(i).bookshelf, false, true);
                if (temp > 0) {
                    space.player.get(i).score += temp;
                    space.player.get(i).goalReached[0] = true;
                }
            }
            if(!space.player.get(i).goalReached[1]) {
                temp = master.CheckCommonGoal(space.player.get(i).bookshelf, true, false);
                if (temp > 0) {
                    space.player.get(i).score += temp;
                    space.player.get(i).goalReached[1] = true;
                }
            }

        }
    }

    /**
     * The player can end his turn in any moment if he has started his turn, also it is not required to make a move or
     * to check the score.
     * If the board is empty or there are no drawable items, the board is refilled.
     * If the Bookshelf of the player that is ending his turn is full, the last round starts and 1 score point is given.
     * The new PlayerToPlay is chosen.
     *
     * @param username it's the playerToPlay
     * @return true if the correct player has ended the turn.
     */
    public boolean masterEndTurn( String username ) {
        if (playerToPlay.equals(username)) {
            if(master.round.last){
                if(username.equals(master.FirstPlayerSeat.username)){
                    space.winner = space.calculateScore();
                    IsOver = true;
                }
            }
            if(space.board.IsToBeRestored()){
                space.board.restore();
            }
            if (space.player.get(search(this.playerToPlay)).bookshelf.IsFull) {
                if(!master.round.last){
                    space.player.get(search(this.playerToPlay)).score += 1;
                }
                master.round.last = true;
            }
            CheckScore(username);
            this.playerToPlay = master.ChooseNextPlayer();
            space.player.get(search(username)).bookshelf.itemToPut.clear();
            space.board.itemCounter = 0;
            space.drawCounter = 0;
            return true;
        }
        return false;
    }

    /** This method allow the server to force the end of the playerToPlay's turn by resetting his draw ( if he didn't
     * put the drawn items in his bookshelf ).
     *
     * @param username it's the playerToPlay
     */
    public void forcedEndTurn( String username ){
        this.playerToPlay = master.ChooseNextPlayer();
        space.resetDraw(search(username));
        space.player.get(search(username)).bookshelf.itemToPut.clear();
    }

    public int getScore(String username){
        return space.player.get(search(username)).score;
    }

    public int search(String username){
        for(int i=0; i < this.LobbySize; i++){
            if(space.player.get(i).username.equals(username)){return i;}
        }
        return -1;
    }

}
