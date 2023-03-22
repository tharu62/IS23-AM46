package it.polimi.ingsw.MODEL;

public class GAME {
    MASTER master= new MASTER();
    SPACE space= new SPACE();
    int playerNumber=0;
    String playerToPlay;
    int n;
    int m;

    public void addPlayer(String username){
        space.player.get(this.playerNumber).username=username;
        master.player=space.player;
        this.playerNumber++;
    }

    public void setBoard(){
        space.setBoard(this.playerNumber);
    }

    public void DrawCommonGoalCards(){
        master.setFirstDraw(this.playerNumber);
    }

    public void DrawPersonalGoalCards(){
        space.player.get(1).drawPersonalGoalCard();
    }

    public void ChooseFirstPlayerSeat(){
        master.ChooseFirstPlayerSeat();
    }

    public void masterStartTurn() {
        this.playerToPlay = master.ChooseNextPlayer();
        if (master.checkIfLastTurn(space.player.get(1).bookshelf)) {
            /** COMPARO LO SCORE DI TUTTI I MODEL.PLAYER da space.calculateScore()
             * E DICHIARO IL VINCITORE
             */
        }
    }

    public boolean playerDrawItem(){
        return space.draw(this.playerToPlay, n, m);     /** SE RITORNA TRUE PeSCATA ESEGUITA CORRETTAMENTE **/
    }

    public void playerPutItems(int m,int a,int b, int c){
        space.placeItem(m,a,b,c);
    }

    public void PlayerWantsToCheckScore(){
        if(space.player.get(1).goalReached){
            System.out.println("HAI GIA' OTTENUTO GLI OBIETTIVI COMUNI! NON PUOI PRENDERE ALTRI MODEL.TOKEN!");
        }
        else{
        space.player.get(1).score+= master.CheckCommonGoal(space.player.get(1).bookshelf);
        }
        if(space.player.get(1).bookshelf.IsFull){
            master.round.last=true;
        }
    }

}
