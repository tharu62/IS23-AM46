package it.polimi.ingsw.VIEW;

public class Coord {
    double x, y;
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void moveX(double deltaX) {
        this.x+=deltaX;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
