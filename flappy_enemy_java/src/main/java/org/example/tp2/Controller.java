package org.example.tp2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller {
    private FlappyEnemy flappyEnemy;

    //deplacementn vertical de lennemi

    public static void checkCollisions(View view, Game game) {
        Set<Hero> herosList = new HashSet<>();
        Set<Coins> coinsList = new HashSet<>();
        Set<Bullet> bulletList = new HashSet<>();
        Enemy enemy = game.getEnemy();
        for (Coins coin : game.getCoinsList()) {
            if (Game.isCollision(enemy, coin)) {
                 //retire la piece de la scene
                enemy.collectCoins(coin.getValue());
                view.updateScore(enemy.getCoins());
                view.removeCoin(coin);

            } else {
                coinsList.add(coin);
            }
        }

        for (Hero hero : game.getHerosList()) {
            if (Game.isCollision(enemy, hero)) {
                //retire la piece de la scene
              //  enemy.collectCoins(hero.getValue());
                //view.updateScore(enemy.getCoins());
                view.removeHero(hero);   //seulement si le hero meurt!!
                if (hero.getType() == 1){
                    enemy.die();
                };
                if (hero.getType() == 2){
                    enemy.loseCoins(10);
                };
                if (hero.getType() == 3){
                    enemy.loseHealth(enemy.getHealth() / 2);
                }
                view.updateLife(enemy.getHealth());
                view.updateScore(enemy.getCoins());
            } else {
                herosList.add(hero);
            }

        }
//creer une liste bullet
        game.getHerosList().clear();
        game.getHerosList().addAll(herosList);
        for (Bullet bullet: game.getBulletList()) {
            for (Hero hero : game.getHerosList()) {
                if (Game.isCollision(hero, bullet)) {
                    //view.removeHero(hero);   //seulement si le hero meurt!!
                    if (hero.getType() == 1) {
                        game.getEnemy().collectCoins(5);
                        // view.removeHero(hero);
                    }

                    if (hero.getType() == 2) {
                        game.getEnemy().collectCoins(8);
                        //view.removeHero(hero);
                    }

                    if (hero.getType() == 3) {
                        game.getEnemy().collectCoins(7);
                    }

                    //view.removeHero(hero);
                    view.removeBullet(bullet);
                    view.removeHero(hero);

                } else {
                    bulletList.add(bullet);
                    herosList.add(hero);
                }
            }
            game.getHerosList().clear();
            game.getHerosList().addAll(herosList);
        }

        game.getCoinsList().clear();
        game.getCoinsList().addAll(coinsList);
        game.getHerosList().clear();
        game.getHerosList().addAll(herosList);
        game.getBulletList().clear();
        game.getBulletList().addAll(bulletList);
    }




}