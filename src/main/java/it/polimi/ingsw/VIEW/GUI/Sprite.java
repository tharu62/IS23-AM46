package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite implements Movable {
    public double x, y;
    public ImageView fxid;

    public Sprite(int x, int y, ImageView fxid) {
        this.x = x;
        this.y = y;
        this.fxid = fxid;
    }

    public Sprite(ImageView fxid){
        this.fxid = fxid;
    }

    @Override
    public void MoveUp(){
        this.y-=1;
        updatePos();
    }
    @Override
    public void MoveDown(){
        this.y+=1;
        updatePos();
    }
    @Override
    public void MoveLeft(){
        this.x-=1;
        updatePos();
    }
    @Override
    public void MoveRight(){
        this.x+=1;
        updatePos();
    }
    void updatePos(){
        this.fxid.setX(this.x);
        this.fxid.setY(this.y);
    }
    public ImageView getImageView() {
        return this.fxid;
    }
    public void setImage(String imageName){
        Image img = new Image(imageName);
        this.fxid = new ImageView(img);
    }
}
