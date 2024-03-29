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
import java.util.List;

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

    /******************************************************************************************************************/

    /**
     * This method updates the SpritesBoard of the GUI Gameplay scene with the given array of items.
     *
     * @param grid is the given Board.
     */
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

    /**
     * This method updates the SpriteBookshelf of the GUI Gameplay scene with the given array of items.
     *
     * @param bookshelf is the given bookshelf.
     */
    @Override
    public void updateBookshelf( item[][] bookshelf) {
        for(int i=0; i < 6; i++){
            for(int j=0; j < 5; j++){
                if(bookshelf[i][j]==item.CATS){
                    updateSlotCats(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.GAMES){
                    updateSlotGames(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.BOOKS){
                    updateSlotBooks(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.PLANTS){
                    updateSlotPlants(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.TROPHIES){
                    updateSlotTrophies(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.FRAMES){
                    updateSlotFrames(GUI.gameplayData.SpriteBookshelf[i][j].fxid);
                }
                if(bookshelf[i][j]==item.OBJECT){
                    GUI.gameplayData.SpriteBookshelf[i][j].fxid.setImage(null);
                }
            }
        }
    }

    /**
     * This method moves all the messages upward by one place and insert the given message in the first 'cell' of the
     * message list. ( the messages in the scene are TextFields with unique fx-ids, so the structure of the messages
     * is not actually a list but a series of TexFields with unique fx-ids with no relationship between each other )
     *
     * @param message
     * @param Private
     */
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
                    GUI.gameSceneController.mes0.setText(message.header[0] + " <private> : " + message.text);
                }else{
                    GUI.gameSceneController.mes0.setText( message.header[0] + " <public> : " + message.text);
                }
            }
        }
    }

    /**
     * This method takes the chat input from the player and sends it to the server, then it shows the message in the
     * scene. The method splits the message if necessary ( default split threshold : 45 char ) and scrolls the chat.
     *
      */
    @Override
    synchronized public void chatEnter() {

        if(!GUI.chatData.privateMess){
            try {
                com.sendChat(GUI.controller.username, GUI.chatData.chatString, "everyone");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(!GUI.chatData.chatString.equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if( GUI.chatData.chatString.length() > 45){
                    int x = 0;
                    int y = 45;
                    while(x+1 < GUI.chatData.chatString.length()){
                        char[] temp = new char[45];
                        GUI.chatData.chatString.getChars(x,x+y, temp,0);
                        //
                        MESSAGE m = new MESSAGE();
                        m.text = String.valueOf(temp);
                        //
                        scrollChat(m,false);
                        x+=45;
                        if((GUI.chatData.chatString.length()-x) <= 45){
                            y = GUI.chatData.chatString.length()-x;
                        }
                    }
                }else{
                    MESSAGE m = new MESSAGE();
                    m.text = GUI.chatData.chatString;
                    m.header[0] = controller.username;
                    scrollChat(m,false);
                }
                GUI.gameSceneController.chatInput.clear();
                GUI.chatData.chatString = "";
            }
        }
        if(GUI.chatData.privateMess){
            MESSAGE m = new MESSAGE();
            m.text = GUI.chatData.chatString;
            m.header[0] = controller.username;
            m.header[1] = GUI.chatData.privateReceiver;
            try {
                com.sendChat(GUI.controller.username, GUI.chatData.chatString, GUI.chatData.privateReceiver);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
            if(GUI.chatData.chatString.length() > 45){
                int x = 0;
                int y = 45;
                while(x+1 < GUI.chatData.chatString.length()){
                    char[] temp = new char[45];
                    GUI.chatData.chatString.getChars(x,x+y, temp,0);
                    //
                    MESSAGE n = new MESSAGE();
                    n.text = String.valueOf(temp);
                    n.header[0] = controller.username;
                    n.header[1] = m.header[1];
                    //
                    scrollChat(n,true);
                    x+=45;
                    if((GUI.chatData.chatString.length()-x) <= 45){
                        y = GUI.chatData.chatString.length()-x;
                    }
                }
            }else{
                scrollChat(m,true);
            }
            GUI.gameSceneController.chatInput.clear();
            GUI.chatData.privateMess = false;
            GUI.chatData.chatString = "";
            GUI.gameSceneController.chatInput.setPromptText("type something...");
        }
    }

    /**
     * This method switches the selected item in the draw pile with the item on top of it.
     * (if the selected item is already on the top, the method does nothing)
     * The method also saves the order in which the item selected must be placed in the bookshelf.
     *
     */
    @Override
    public void drawUp() {
        Image temp;
        String tempOrder;
        if(GUI.gameplayData.drawInProgress){
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[1].fxid){
                temp = GUI.gameplayData.DrawPile[0].fxid.getImage();
                GUI.gameplayData.DrawPile[0].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[1].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[0];
                tempOrder = GUI.gameplayData.drawPileOrder[0];
                GUI.gameplayData.drawPileOrder[0] = GUI.gameplayData.drawPileOrder[1];
                GUI.gameplayData.drawPileOrder[1] = tempOrder;
            }
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[2].fxid){
                temp = GUI.gameplayData.DrawPile[1].fxid.getImage();
                GUI.gameplayData.DrawPile[1].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[2].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[1];
                tempOrder = GUI.gameplayData.drawPileOrder[1];
                GUI.gameplayData.drawPileOrder[1] = GUI.gameplayData.drawPileOrder[2];
                GUI.gameplayData.drawPileOrder[2] = tempOrder;
            }
        }
    }

    /**
     * This method switches the selected item in the draw pile with the item under it.
     * (if the selected item is already on the bottom, the method does nothing)
     * The method also saves the order in which the item selected must be placed in the bookshelf.
     *
     */
    @Override
    public void drawDown() {
        Image temp;
        String tempOrder;
        if(GUI.gameplayData.drawInProgress){
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[1].fxid){
                temp = GUI.gameplayData.DrawPile[2].fxid.getImage();
                GUI.gameplayData.DrawPile[2].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[1].fxid.setImage(temp) ;
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[2];
                tempOrder = GUI.gameplayData.drawPileOrder[2];
                GUI.gameplayData.drawPileOrder[2] = GUI.gameplayData.drawPileOrder[1];
                GUI.gameplayData.drawPileOrder[1] = tempOrder;
            }
            if(GUI.gameplayData.selectedItem.fxid == GUI.gameplayData.DrawPile[0].fxid){
                temp = GUI.gameplayData.DrawPile[1].fxid.getImage();
                GUI.gameplayData.DrawPile[1].fxid.setImage(GUI.gameplayData.selectedItem.getImageView().getImage());
                GUI.gameplayData.DrawPile[0].fxid.setImage(temp);
                GUI.gameplayData.selectedItem = GUI.gameplayData.DrawPile[1];
                tempOrder = GUI.gameplayData.drawPileOrder[1];
                GUI.gameplayData.drawPileOrder[1] = GUI.gameplayData.drawPileOrder[0];
                GUI.gameplayData.drawPileOrder[0] = tempOrder;
            }
        }
    }

    /**
     * This method updates the draw process and sends a draw request at the server.
     * if the draw is valid or not the player is notified.
     *
     * @param mouseEvent is required to get the row and column of the selected item in the SpriteBoard.
     */
    @Override
    public void drawItem(MouseEvent mouseEvent) {
        SearchIndex searchBoard = new SearchIndex();
        if(GUI.gameplayData.drawInProgress && GUI.gameplayData.drawCounter >= 0){
            int row = searchBoard.findRowFromBoard(GUI.gameplayData.SpritesBoard, ((ImageView) mouseEvent.getSource()));
            int col = searchBoard.findColFromBoard(GUI.gameplayData.SpritesBoard, ((ImageView) mouseEvent.getSource()));
            try {
                com.draw(controller.username, row, col, controller);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(replyDraw()){
              GUI.gameplayData.DrawPile[GUI.gameplayData.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
              GUI.gameplayData.drawCounter --;
            }else{
                gui.Notify(" DRAW NOT VALID, RETRY. ");
            }
        }else{
            gui.Notify(" DRAW LIMIT REACHED, NOW YOU HAVE TO PUT ");
        }
    }

    /**
     * This method updates the put process and sends a put request at the server.
     * The method finds the correct order of the drawn items and sends them to the server.
     * If the put is valid the method resets the draw attributes and the selected column of the bookshelf.
     * If the put is not valid the player is notified.
     *
     */
    @Override
    public void putItem() {
        SearchIndex searchIndex = new SearchIndex();
        int a = searchIndex.findIndexFromPile(GUI.gameplayData.drawPileOrder, GUI.gameSceneController.firstDraw);
        int b = searchIndex.findIndexFromPile(GUI.gameplayData.drawPileOrder, GUI.gameSceneController.secondDraw);
        int c = searchIndex.findIndexFromPile(GUI.gameplayData.drawPileOrder, GUI.gameSceneController.thirdDraw);
        try {
            com.put(controller.username, GUI.gameplayData.selectedCol, a, b,  c, controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(replyPut()){
            try {
                new StandardSpriteDataStructure().setDrawPileOrder(GUI.gameplayData.drawPileOrder);
                GUI.gameplayData.DrawPile[0].setImage(null);
                GUI.gameplayData.DrawPile[1].setImage(null);
                GUI.gameplayData.DrawPile[2].setImage(null);
                GUI.gameplayData.drawCounter = 2;
                com.endTurn(controller.username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }else{
            gui.Notify(" PUT NOT VALID, RETRY. ");
        }
        GUI.gameplayData.drawInProgress = true;
        GUI.gameplayData.selectedCol = -1;
    }

    @Override
    public void setCommonGoals(List<Integer> cardID, List<Integer> token) {
        SearchGoalCards searchGoalCards = new SearchGoalCards();
        GUI.gameSceneController.commonGoal1.setImage(searchGoalCards.search(CardType.COMMON, cardID.get(0)));
        GUI.gameSceneController.commonGoal2.setImage(searchGoalCards.search(CardType.COMMON, cardID.get(1)));
        GUI.gameSceneController.scoreToken1.setImage(searchGoalCards.searchToken(token.get(0)));
        GUI.gameSceneController.scoreToken2.setImage(searchGoalCards.searchToken(token.get(1)));
    }


    @Override
    public void setPersonalGoal(int cardID) {
        SearchGoalCards searchGoalCards = new SearchGoalCards();
        GUI.gameSceneController.personalGoal.setImage(searchGoalCards.search(CardType.PERSONAL, cardID));
    }

    @Override
    public void setPlayers(List<String> players) {
        int counter = 0;
        for(String player : players){
            if(!player.equals(controller.username)){
                if(counter == 3){
                    GUI.gameSceneController.player_3.setText(player);
                    counter ++;
                }
                if(counter == 2){
                    GUI.gameSceneController.player_2.setText(player);
                    counter ++;
                }
                if(counter == 1){
                    GUI.gameSceneController.player_1.setText(player);
                    counter ++;
                }
                if(counter == 0){
                    GUI.gameSceneController.player_0.setText(player);
                    counter ++;
                }
            }
        }
    }

    @Override
    public void setScore(int score) {
        GUI.gameSceneController.score.setText(String.valueOf(score));
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

    /**
     * This method checks if a reply for a put request has been received.
     * If the reply arrives, the value of the reply is returned, else it loops forever.
     *
     * @return result of draw request.
     */
    @Override
    synchronized public boolean replyDraw() {
        while(true){
            if(controller.getReplyDraw()){
                controller.reply_draw = false;
                return controller.draw_valid;
            }
        }
    }

    /**
     * This method checks if a reply for end turn request has been sent.
     * If the reply arrives, the value of the reply is returned, else it loops forever.
     */
    @Override
    synchronized public boolean replyPut() {
        while(true){
            if(controller.getReplyPut()){
                controller.reply_put = false;
                return controller.put_valid;
            }
        }
    }

    /**
     * This method allow the GUI to replace the reference to the client if the connection is lost and the reconnection
     * protocol has started.
     *
     * @param clientTCP is the client tcp reference.
     */
    @Override
    public void replaceClient(ClientTCP clientTCP) {
        com.replaceClient(clientTCP);
    }

    /**
     * This method allow the GUI to replace the reference to the client if the connection is lost and the reconnection
     * protocol has started.
     *
     * @param clientRMI is the client rmi reference
     */
    @Override
    public void replaceClient(ClientRMI clientRMI) {
        com.replaceClient(clientRMI);
    }

}

