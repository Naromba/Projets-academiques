package org.example.tp2;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Enemy implements  Entity{

    private Image image;



    private double x;


    private double y;
    private int coins;

    private int health;
    private double speedX;
    private double speedY;
    private double lastAttack;

    private int gravity;
    private double maxSpeed = 300;
    private double jumpSpeed = -150;
    private double bounce = -1.0;
    private Circle body;



    private ImageView view;

    public Enemy (double x, double y){
        this.image = new Image("file:assets/sorciere.png");
        this.x = x;
        this.y = y;
        body = new Circle(x, y, 30);
        this.coins = 0;
        this.speedX = 120;
        this.speedY = 0;
        this.gravity = 500;
        this.view = new ImageView(image);
        this.view.setFitWidth(60); // Redimensionner la largeur de l'ennemi
        this.view.setFitHeight(60); // Redimensionner la hauteur de l'ennemi
        this.view.setLayoutY(y);
        this.view.setLayoutX(x);
        this.lastAttack = 0;
        this.health = 100;
    }

    public void collectCoins(int value) {
        this.coins += value;
        this.speedX += 10;
        this.gravity += 15;
    }


    public int getCoins() {
        return coins;
    }

    public double getY() {
        return y;
    }
    @Override
    public Bounds getHitbox() {
       // return new Circle(this.view.getX(), this.view.getY(), 30).getBoundsInParent();
        return this.view.getBoundsInParent();
    }
    public ImageView getView() {
        return view;
    }
    public double getX() {
        return x;
    }
    public int getHealth() {
        return health;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void die(){
        //GAME OVER
        //AFFICHER UN GAME OVER!!!
        this.health = 0;
    }

    public void loseHealth(int amount){
        this.health -= amount;
        if (this.health <0){
            this.health = 0;
        }
    }

    public void loseCoins(int amount){
        this.coins -= amount;
        if (this.coins < 0){
            this.coins = 0;
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }


    public Bullet attack(){
        long time = System.currentTimeMillis();
        if (time - lastAttack >= 1000) {
            lastAttack = time;
            return new Bullet(this.x, this.y);
        }
        return null;
    }
    public void update(double deltaTime) {
        // Apply gravity
        speedY += gravity * deltaTime;

        // Limit speed
        if (speedY > maxSpeed) {
            speedY = maxSpeed;
        }

        // Move enemy
        y += speedY * deltaTime;

        // Check for collision with screen bounds and bounce
        if (y < 0 || y > View.getHEIGHT()) {
            speedY *= -1;
        }

        // If enemy hits the ground, make it jump
        if (y >= View.getHEIGHT()) {
            jump();
        }

        // Update ImageView position
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public void jump() {
        // Apply a negative speed to make the enemy jump
        speedY = jumpSpeed;
    }

}


