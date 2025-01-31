package org.example.tp2;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Bullet implements Entity {



    private double speed;



    private double x;



    private double y;



    private Circle circle;

    public Bullet(double x, double y){
        speed = 10;
        this.x = x;
        this.y = y;
        circle = new Circle(x, y, 3);

    }




    public void move() {
        this.x += speed;

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public Circle getCircle() {
        return circle;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void update(double time) {

    }

    @Override
    public Bounds getHitbox() {
        //return this.imageView.getBoundsInParent();
        return this.circle.getBoundsInParent();

    }
}
