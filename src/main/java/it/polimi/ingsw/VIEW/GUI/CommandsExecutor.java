package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.RMI;
import it.polimi.ingsw.VIEW.TCP;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.rmi.RemoteException;

public class CommandsExecutor implements GUI_commands {
    static CommunicationProtocol com;
    static GUI gui;
    static CONTROLLER controller;

    public CommandsExecutor( CONTROLLER controller, ClientRMI client, GUI gui) {
    com = new RMI(client);
    CommandsExecutor.gui = gui;
    CommandsExecutor.controller = controller;
    }

    public CommandsExecutor( CONTROLLER controller, ClientTCP client, GUI gui) {
        com = new TCP(client);
        CommandsExecutor.gui = gui;
        CommandsExecutor.controller = controller;
    }

    @Override
    public void updateGrid(item[][] grid) {
        for(int i=0; i < 9; i++){
            for(int j=0; j < 9; j++){
                if(grid[i][j]==item.CATS){
                    updateSlotCats(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.GAMES){
                    updateSlotGames(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.BOOKS){
                    updateSlotBooks(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.PLANTS){
                    updateSlotPlants(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.TROPHIES){
                    updateSlotTrophies(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.FRAMES){
                    updateSlotFrames(GUI.gameplayData.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.OBJECT){
                    GUI.gameplayData.SpritesBoard[i][j].fxid.setImage(null);
                }
            }
        }
    }

    @Override
    public void updateBookshelf() {
        StandardSprite standardSprite = new StandardSprite();
        int col = GUI.gameplayData.selectedCol;
        for(int i=5; i >= 0; i--){
            for(int j=4; j >= 0; j--){
                if(j == col){
                    if(GUI.gameplayData.SpriteBookshelf[i][j].fxid.getImage() == null){
                        if(GUI.gameplayData.DrawPile[2].fxid.getImage() != null){
                            GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[2].fxid.getImage());
                            GUI.gameplayData.DrawPile[2].fxid.setImage(null);
                        }else{
                            if(GUI.gameplayData.DrawPile[1].fxid.getImage() != null){
                                GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[1].fxid.getImage());
                                GUI.gameplayData.DrawPile[1].fxid.setImage(null);
                            }else{
                                if(GUI.gameplayData.DrawPile[0].fxid.getImage() != null){
                                    GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[0].fxid.getImage());
                                    GUI.gameplayData.DrawPile[0].fxid.setImage(null);
                                }
                            }
                        }
                    }
                }
            }
        }
        GUI.gameplayData.DrawPile = standardSprite.setDrawPile(GUI.gameSceneController.firstDraw, GUI.gameSceneController.secondDraw, GUI.gameSceneController.thirdDraw);
        try {
            com.endTurn(controller.username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    synchronized public void scrollChat(MESSAGE message, boolean Private) {
        for(int i=6;i>0;i--){
            if(i==6){
                GUI.gameSceneController.mes6.setText(GUI.gameSceneController.mes5.getText());
            }
            if(i==5){
                GUI.gameSceneController.mes5.setText(GUI.gameSceneController.mes4.getText());
            }
            if(i==4){
                GUI.gameSceneController.mes4.setText(GUI.gameSceneController.mes3.getText());
            }
            if(i==3){
                GUI.gameSceneController.mes3.setText(GUI.gameSceneController.mes2.getText());
            }
            if(i==2){
                GUI.gameSceneController.mes2.setText(GUI.gameSceneController.mes1.getText());
            }
            if(i==1){
                GUI.gameSceneController.mes1.setText(GUI.gameSceneController.mes0.getText());
                if(Private){
                    GUI.gameSceneController.mes0.setText("<private: " + message.header[1] + "> " + message.text);
                }else{
                    GUI.gameSceneController.mes0.setText("< " + message.header[0] + "> " + message.text);
                }
            }
        }
    }

    @Override
    synchronized public void chatEnter() {

        //TODO creare un oggetto che permetta di spezzare le stringe per avitare di avere codice troppo pesante qui.

        if(!GUI.chatData.privateMess){
            try {
                com.sendChat(GUI.chatData.stringBuilder.toString(), "everyone", GUI.controller.username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(!GUI.chatData.stringBuilder.toString().equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if( GUI.chatData.stringBuilder.toString().length() > 70){
                    int x = 0;
                    int y = 70;
                    while(x+1 < GUI.chatData.stringBuilder.toString().length()){
                        char[] temp = new char[70];
                        GUI.chatData.stringBuilder.toString().getChars(x,x+y, temp,0);
                        //
                        MESSAGE m = new MESSAGE();
                        m.text = String.valueOf(temp);
                        //
                        scrollChat(m,false);
                        x+=70;
                        if((GUI.chatData.stringBuilder.toString().length()-x) <= 70){
                            y = GUI.chatData.stringBuilder.toString().length()-x;
                        }
                    }
                }else{
                    MESSAGE m = new MESSAGE();
                    m.text = GUI.chatData.stringBuilder.toString();
                    scrollChat(m,false);
                }
                GUI.gameSceneController.chatInput.clear();
                GUI.chatData.stringBuilder = new StringBuilder();
            }
        }
        if(GUI.chatData.privateMess){
            MESSAGE m = new MESSAGE();
            m.text = GUI.chatData.privateStringBuilder.toString();
            m.header[1] = GUI.chatData.privateReceiver;
            try {
                com.sendChat(GUI.chatData.privateStringBuilder.toString(), GUI.chatData.privateReceiver, GUI.controller.username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
            if(GUI.chatData.privateStringBuilder.toString().length() > 70){
                int x = 0;
                int y = 70;
                while(x+1 < GUI.chatData.privateStringBuilder.toString().length()){
                    char[] temp = new char[70];
                    GUI.chatData.privateStringBuilder.toString().getChars(x,x+y, temp,0);
                    //
                    MESSAGE n = new MESSAGE();
                    n.text = String.valueOf(temp);
                    n.header[1] = m.header[1];
                    //
                    scrollChat(n,true);
                    x+=70;
                    if((GUI.chatData.privateStringBuilder.toString().length()-x) <= 70){
                        y = GUI.chatData.privateStringBuilder.toString().length()-x;
                    }
                }
            }else{
                scrollChat(m,true);
            }
            GUI.gameSceneController.chatInput.clear();
            GUI.chatData.privateMess = false;
            GUI.chatData.privateStringBuilder = new StringBuilder();
            GUI.gameSceneController.chatInput.setPromptText("type something...");
        }
    }

    @Override
    public void drawUp() {
        Image temp;
        if(GUI.gameplayData.drawInProgress){
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[1].fxid){
                temp = GUI.gameplayData.DrawPile[0].fxid.getImage();
                GUI.gameplayData.DrawPile[0].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[1].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[0];
            }
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[2].fxid){
                temp = GUI.gameplayData.DrawPile[1].fxid.getImage();
                GUI.gameplayData.DrawPile[1].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[2].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[1];
            }
        }
    }

    @Override
    public void drawDown() {
        Image temp;
        if(GUI.gameplayData.drawInProgress){
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[1].fxid){
                temp = GUI.gameplayData.DrawPile[2].fxid.getImage();
                GUI.gameplayData.DrawPile[2].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[1].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[2];
            }
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[0].fxid){
                temp = GUI.gameplayData.DrawPile[1].fxid.getImage();
                GUI.gameplayData.DrawPile[1].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[0].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[1];
            }
        }
    }

    @Override
    public void selectItemToDraw(MouseEvent mouseEvent) {
        SearchIndex searchBoard = new SearchIndex();
        if(GUI.gameplayData.drawInProgress && GUI.gameplayData.drawCounter < 3){
            int row = searchBoard.findRowFromBoard(GUI.gameplayData.SpritesBoard, ((ImageView) mouseEvent.getSource()));
            int col = searchBoard.findColFromBoard(GUI.gameplayData.SpritesBoard, ((ImageView) mouseEvent.getSource()));
            try {
                com.draw(controller.username, row, col, controller);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(replyDraw()){
              GUI.gameplayData.DrawPile[GUI.gameplayData.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
              GUI.gameplayData.drawCounter++;
            }else{
                gui.setNotification(" DRAW NOT VALID, RETRY. ");
            }
        }else{
            gui.setNotification(" DRAW LIMIT REACHED, NOW YOU HAVE TO PUT ");
        }
    }

    @Override
    public void putDrawInBookshelf() {
        SearchIndex searchIndex = new SearchIndex();
        int a = searchIndex.findIndexFromPile(GUI.gameplayData.DrawPile, GUI.gameSceneController.firstDraw);
        int b = searchIndex.findIndexFromPile(GUI.gameplayData.DrawPile, GUI.gameSceneController.secondDraw);
        int c = searchIndex.findIndexFromPile(GUI.gameplayData.DrawPile, GUI.gameSceneController.thirdDraw);
        try {
            com.put(controller.username, GUI.gameplayData.selectedCol, a, b,  c, controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(replyPut()){
            updateBookshelf();
        }else{
            gui.setNotification(" PUT NOT VALID, RETRY. ");
        }
        GUI.gameplayData.selectedCol = -1;
    }

    @Override
    public void setCommonGoals() {
        SearchGoalCards searchGoalCards = new SearchGoalCards();
        GUI.gameSceneController.commonGoal1.setImage(searchGoalCards.search(CardType.COMMON, GUI.controller.cards.get(0)));
        GUI.gameSceneController.commonGoal2.setImage(searchGoalCards.search(CardType.COMMON, GUI.controller.cards.get(1)));
        //GUI.gameSceneController.scoreToken1.setImage(TODO);
        //GUI.gameSceneController.scoreToken1.setImage(TODO);
    }

    @Override
    public void setPersonalGoal() {
        try {
            com.getPersonalGoal(GUI.controller.PersonalGoalCardID, GUI.controller.username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(controller.getReplyPersonal()){
            SearchGoalCards searchGoalCards = new SearchGoalCards();
            GUI.gameSceneController.personalGoal.setImage(searchGoalCards.search(CardType.PERSONAL, GUI.controller.PersonalGoalCardID));
        }
    }

    @Override
    public void login() {

    }

    private void updateSlotCats(ImageView imageViewID){
        imageViewID.setImage(new Image("Gatti1.1.png"));
    }

    private void updateSlotGames(ImageView imageViewID){
        imageViewID.setImage(new Image("Giochi1.1.png"));
    }

    private void updateSlotBooks(ImageView imageViewID){
        imageViewID.setImage(new Image("Libri1.1.png"));
    }

    private void updateSlotPlants(ImageView imageViewID){
        imageViewID.setImage(new Image("Piante1.1.png"));
    }

    private void updateSlotTrophies(ImageView imageViewID){
        imageViewID.setImage(new Image("Trofei1.1.png"));
    }

    private void updateSlotFrames(ImageView imageViewID){
        imageViewID.setImage(new Image("Cornici1.1.png"));
    }

    @Override
    synchronized public boolean replyDraw() {
        while(true){
            if(controller.getReplyDraw()){
                controller.reply_draw = false;
                return controller.draw_valid;
            }
        }
    }

    @Override
    synchronized public boolean replyPut() {
        while(true){
            if(controller.getReplyPut()){
                controller.reply_put = false;
                return controller.put_valid;
            }
        }
    }
}
