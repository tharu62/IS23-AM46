package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.control.TextField;

public class GameplayData {
    public int drawCounter = 0;
    public boolean drawInProgress = false, gameSceneOpen = false;
    public Sprite selectedItem = new Sprite(null);
    public int selectedCol = -1;
    public Sprite[][] SpritesBoard;
    public Sprite[] DrawPile = new Sprite[3];
    public Sprite[][] SpriteBookshelf = new Sprite[6][5];

}
