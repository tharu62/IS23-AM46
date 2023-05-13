package it.polimi.ingsw.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite implements Movable {
    double x, y;
    String f_name;
    ImageView imageView;

    public Sprite(double x, double y, String f_name) {
        this.x = x;
        this.y = y;
        this.f_name = f_name;
        Image img = new Image(f_name);
        this.imageView = new ImageView(img);
    }

    @Override
    public void MoveUp(){
        this.y-=10;
        updatePos();
    }
    @Override
    public void MoveDown(){
        this.y+=10;
        updatePos();
    }
    @Override
    public void MoveLeft(){
        this.x-=10;
        updatePos();
    }
    @Override
    public void MoveRight(){
        this.x+=10;
        updatePos();
    }
    void updatePos(){
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
    }
    public ImageView getImageView() {
        return this.imageView;
    }
}
