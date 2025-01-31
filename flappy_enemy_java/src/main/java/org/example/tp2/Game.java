package org.example.tp2;

import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final int MAX_SIZE = 1000;


    private Enemy enemy;
    private List<Hero> herosList;
    private List<Coins> coinsList;
    private int totalPieces;
    private Text texte;



    private  List<Bullet> bulletList;

    public Game() {
        // Initialiser le jeu ici
        Image enemyImage = new Image("file:assets/sorciere.JPG");
        double initialX = 0;
        double initialY = (View.getHEIGHT() - enemyImage.getHeight()) / 2;
        enemy = new Enemy(initialX, initialY);
        coinsList = new ArrayList<>();
        herosList = new ArrayList<>();
        bulletList = new ArrayList<>();
        //totalPieces = 1;


    }

    public  List<Coins> getCoinsList() {
        return coinsList;
    }

    public List<Hero> getHerosList() {
        return herosList;
    }

    public Enemy getEnemy() {
        return enemy;
    }
    public void jumpEnemy() {
        // Faites sauter l'ennemi
        enemy.jump();
    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public int getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(int totalPieces) {
        this.totalPieces = totalPieces;
    }

    public Text getTexte() {
        return texte;
    }

    public void setTexte(Text texte) {
        this.texte = texte;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(List<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    public void clearBullets() {
        bulletList.clear();
    }

    public static boolean isCollision(Entity entity1, Entity entity){
        return entity1.getHitbox().intersects(entity.getHitbox());

    }




}
