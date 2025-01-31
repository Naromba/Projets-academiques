package org.example.tp2;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Coins implements Entity{
    private Image image;

    private double x;
    private double y;
    private Rectangle hitbox;
    private int value;
    private ImageView view;

    public Coins (double x, double y, int value){
        this.image = new Image("file:assets/coin.png");
        this.x = x;
        this.y = y;
        //view.getBoundsInParent();
        this.value = value;
        this.view = new ImageView(image);
        this.view.setFitHeight(30);
        this.view.setFitWidth(30);
        this.view.setLayoutX(x);
        this.view.setLayoutY(y);
    }

    public int getValue(){
        return this.value;
    }
    @Override
    public void update(double time) {
        // mettre a jour la position du coin
    }

    @Override
    public Bounds getHitbox() {
        //return new Circle(this.view.getX(), this.view.getY(), this.view.getFitHeight() / 2).getBoundsInParent();
        return this.view.getBoundsInParent();
    }


    public ImageView getImageView () {
        return this.view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }



}
