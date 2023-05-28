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

    /** The player starts his turn, if it's not the last, then round and turn are updated
     * and the player's bookshelves is checked.
     * If it's the last turn the score of each player is calculated checking personal goals and adjacent item_tiles
     * on the bookshelves, then the scores are compared and the winner's name is saved in space.
     * The score from the common goals cannot be checked here. below there is the reason.
     */
    public boolean masterStartTurn(String username) {
        if(this.playerToPlay.equals(username)) {
            if (master.checkIfLastTurn(space.player.get(search(this.playerToPlay)).bookshelf)) {
                space.calculateScore();
                IsOver = true;
                return false;
            }
            return true;
        }
        return false;
    }

    /** The player has to ask the MODEL if he can pick an item_tile. One item_tile at a time.
     * Once he is satisfied with the pick, the picked items are stored in his bookshelf class until he wants to put them in the bookshelf grid.
     *
     * @param n it's the row from witch to pick the item
     * @param m it's the column from witch to pick the item
     * @return  true only if the pick is valid
     */
    public boolean playerDrawItem(String username, int n, int m){
        if (this.playerToPlay.equals(username)){
            if (space.player.get(search(username)).bookshelf.IsFull) return false;
            return space.draw(search(username), n, m);
        }
        return false;
    }

    /** The player chooses the order to put the items in the bookshelf by giving each item_tile a number that goes from 0 to 2.
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
            int i = search(username);
            space.placeItem(i, m, a, b, c);
            if (space.player.get(search(this.playerToPlay)).bookshelf.IsFull) {
                if(!master.round.last){
                    space.player.get(search(this.playerToPlay)).score += 1;
                }
                master.round.last = true;
            }
            return true;
        }
        return false;
    }

    /** The player has the option of checking his score if he believes that he reached a common goal.
     * It is not mandatory each turn, only optional AND it's not checked by the system at the end of the game.
     * If the player doesn't see he achieved a common goal, he will not get the point. Even so you can exploit the system by checking every turn blindly tho...
     * But it follows the game's philosophy, first to see first to get.
     * The method both checks and updates the score based on the common goals available.
     * We implemented two common goals from the beginning, instead of one for the first game and two from the second.
     */
    public void PlayerWantsToCheckScore(String username){
        if (this.playerToPlay.equals(username)) {
            int i = search(playerToPlay);
            int temp;
            if (space.player.get(i).goalReached[0] && space.player.get(i).goalReached[1]) {
                System.out.println("HAI GIA' OTTENUTO GLI OBIETTIVI COMUNI! NON PUOI PRENDERE ALTRI MODEL.TOKEN!");
            }
            if (!space.player.get(i).goalReached[0]) {
                temp = master.CheckCommonGoal(space.player.get(i).bookshelf, false, true);
                if (temp > 0) {
                    space.player.get(i).score += temp;
                    space.player.get(i).goalReached[0] = true;
                }
            } else System.out.println("HAI GIA' OTTENUTO IL PRIMO OBIETTIVO COMUNE!");
            if(!space.player.get(i).goalReached[1]) {
                temp = master.CheckCommonGoal(space.player.get(i).bookshelf, true, false);
                if (temp > 0) {
                    space.player.get(i).score += temp;
                    space.player.get(i).goalReached[1] = true;
                }
            } else System.out.println("HAI GIA' OTTENUTO IL SECONDO OBIETTIVO COMUNE!");
            if (space.player.get(i).bookshelf.IsFull) {
                master.round.last = true;
            }
        }
    }

    /**The player can end his turn in any moment if he has started his turn, also it is not required to make a move nor
     * to ckeck the score.
     * When the player ends his turn the new Player To Play is chosen.
     * @param username it's the playerToPlay
     * @return true if the correct player has ended the turn.
     */
    public boolean masterEndTurn( String username ) {
        if(space.board.IsToBeRestored()){
            space.board.restore();
        }
        if (playerToPlay.equals(username)) {
            this.playerToPlay = master.ChooseNextPlayer();
            space.player.get(search(username)).bookshelf.itemToPut.clear();
            space.board.itemCounter = 0;
            space.i = 0;
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
        space.resetDraw( search(username));
        space.player.get(search(username)).bookshelf.itemToPut.clear();
    }


    public int search(String username){
        for(int i=0; i < this.LobbySize; i++){
            if(space.player.get(i).username.equals(username)){return i;}
        }
        return -1;
    }

}
