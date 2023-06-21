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
            }
        }
    }

    @Override
    public void updateBookshelf() {
        StandardSprite standardSprite = new StandardSprite();
        int col = GUI.gameplayData.selectedCol;
        for(int i=4; i >= 0; i--){
            for(int j=4; j >= 0; j++){
                if(j == col){
                    if(GUI.gameplayData.SpriteBookshelf[i][j].fxid.getImage() == null){
                        if(GUI.gameplayData.DrawPile[2].fxid.getImage() != null){
                            GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[2].fxid.getImage());
                            GUI.gameplayData.DrawPile[2].fxid.setImage(null);
                        }else{
                            if(GUI.gameplayData.DrawPile[1].fxid.getImage() != null){
                                GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[1].fxid.getImage());
                                GUI.gameplayData.DrawPile[2].fxid.setImage(null);
                            }else{
                                GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(GUI.gameplayData.DrawPile[0].fxid.getImage());
                                GUI.gameplayData.DrawPile[0].fxid.setImage(null);
                            }
                        }
                    }
                }
            }
        }
        GUI.gameplayData.DrawPile = standardSprite.setDrawPile(GUI.gameSceneController.firstDraw, GUI.gameSceneController.secondDraw, GUI.gameSceneController.thirdDraw);
    }

    @Override
    public void scrollChat(MESSAGE message, boolean Private) {
        for(int i=4;i>0;i--){
            if(i==4){
                gui.mes4.setText(gui.mes3.getText());
            }
            if(i==3){
                gui.mes3.setText(gui.mes2.getText());
            }
            if(i==2){
                gui.mes2.setText(gui.mes1.getText());
            }
            if(i==1){
                gui.mes1.setText(gui.mes0.getText());
                if(Private){
                    gui.mes0.setText("<private : " + GUI.chatData.privateReceiver + "> " + message.text);
                }else{
                    gui.mes0.setText("<public> " + message.text);
                }
            }
        }
    }

    @Override
    public void chatEnter() {
        if(!GUI.chatData.privateMessRec && !GUI.chatData.privateMess){
            if(!GUI.chatData.stringBuilder.toString().equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if(((String) GUI.chatData.stringBuilder.toString()).length() > 50){
                    int x=0;
                    int y=50;
                    while(x+1 < GUI.chatData.stringBuilder.toString().length()){
                        char[] temp = new char[50];
                        ((String) GUI.chatData.stringBuilder.toString()).getChars(x,x+y, temp,0);
                        System.out.println(String.valueOf(temp));
                        //
                        MESSAGE m = new MESSAGE();
                        m.text =String.valueOf(temp);
                        //
                        scrollChat(m,false);
                        x+=50;
                        if((GUI.chatData.stringBuilder.toString().length()-x) <= 50){
                            y = GUI.chatData.stringBuilder.toString().length()-x;
                        }
                    }
                }else{
                    MESSAGE m = new MESSAGE();
                    m.text =GUI.chatData.stringBuilder.toString();
                    scrollChat(m,false);
                }
                gui.chatInput.clear();
                GUI.chatData.stringBuilder = new StringBuilder();
            }
        }
        if(GUI.chatData.privateMess){
            MESSAGE m = new MESSAGE();
            m.text =GUI.chatData.privateStringBuilder.toString();
            scrollChat(m, true);
            gui.chatInput.clear();
            GUI.chatData.privateMess = false;
            GUI.chatData.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("type something...");
        }
        if(GUI.chatData.privateMessRec){
            GUI.chatData.privateReceiver = GUI.chatData.privateStringBuilder.toString();
            gui.chatInput.clear();
            GUI.chatData.privateMessRec = false;
            GUI.chatData.privateMess = true;
            GUI.chatData.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("Insert text for receiver");
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
            try {
             com.endTurn(controller.username);
            } catch (RemoteException e) {
             throw new RuntimeException(e);
            }
        }else{
            gui.setNotification(" PUT NOT VALID, RETRY. ");
        }
        GUI.gameplayData.selectedCol = -1;
    }

    @Override
    public void setCommonGoals() {

    }

    @Override
    public void setPersonalGoal() {

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
