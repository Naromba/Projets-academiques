package org.example.tp2;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class Hero implements Entity {
    private Image image;
    private double x;


    private double y;

    private int type;
    private int health;
    private int coinsValue;
    private ImageView view;


    public Hero (double x, double y, int type, int size){
        this.image = new Image("file:assets/super1.png");
        this.x = x;
        this.y = y;
        this.type = type;
        this.view = new ImageView(image);
        this.view.setFitHeight(size * 2);
        this.view.setFitWidth(size * 2);
        this.view.setLayoutX(x);
        this.view.setLayoutY(y);

        switch ( type ) {
            case 1: // Corps-à-corps
                this.health = 1;  //a voir
                this.coinsValue = 5;
                break;

            case 2: // Furtif.....voir aussi la vitesse
                this.health = 1;  //a voir
                this.coinsValue = 8;
                break;

            case 3: // Tank.....voir aussi la vitesse
                this.health = 2;  //a voir
                this.coinsValue = 7;
                break;
        }
    }

    @Override
    public void update(double time) {
        // mettre a jour la position du hero
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
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    @Override
    public Bounds getHitbox() {
       // return new Circle(this.view.getX(), this.view.getY(), this.view.getFitHeight() / 2).getBoundsInParent();
        return this.view.getBoundsInParent();
    }

}
