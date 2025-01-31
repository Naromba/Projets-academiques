package org.example.tp2;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;


public class View extends Application {

    private BorderPane root;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 440;
    private Text piece;
    private Text life;
    private Button resume;
    private boolean isPaused = false;
    private Game game;
    private Timeline coinTimeline;
    private Timeline  heroTimeline;
    private Timeline animation;
    private Timeline backgroundTimeline;
    private Timeline collisionTimeline;
    public static int getHEIGHT() {
        return HEIGHT;
    }
    public BorderPane getRoot() {
        return root; // Fournit l'accès au root
    }

    public  View() {
        piece = new Text("Pièces : 0");
    }

   // le hero a creer
    private ImageView createHero(String imagePath, double layoutX, double layoutY) {
        Image heroImage = new Image(imagePath);
        ImageView heroView = new ImageView(heroImage);
        heroView.setFitWidth(100); // Redimensionner la largeur de l'ennemi
        heroView.setFitHeight(100); // Redimensionner la hauteur de l'ennemi
        heroView.setLayoutX(layoutX);
        heroView.setLayoutY(layoutY);
        return heroView;
    }

    private void addCoin(BorderPane root) {
        Random random = new Random();
        double positionY = random.nextDouble() * HEIGHT;// Position Y aléatoire
        Coins coins = new Coins(WIDTH, positionY, 5);
        game.getCoinsList().add(coins);
        // ajout a la scene
        root.getChildren().add(coins.getImageView());
    }

    private void addHero(BorderPane root) {
        Random random = new Random();
        double positionY = random.nextDouble() * HEIGHT; // Position Y aléatoire
        Hero hero = new Hero(WIDTH, positionY, random.nextInt(3) +1 , random.nextInt(35 )+ 10) ;
        game.getHerosList().add(hero);
        // Utiliser la fonction createHero() pour créer un héros avec une position Y aléatoire
        root.getChildren().add(hero.getImageView()); // Ajouter le héros au jeu

    }

    private void action(){
        isPaused = !isPaused;
        if(isPaused){
            stopTimelines();
        }
        else {
            playTimelines();
        }
    }

private Button addButton(HBox parent, String texte) {
    resume = new Button(texte);

    resume.setOnAction(event -> {
        if (!game.getEnemy().isDead()) {
            action();
        } else {
            return;
        }
    });
    parent.getChildren().add(resume);
    resume.focusedProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal) {
            parent.requestFocus();
        }
    });

    return resume;
}



    private void addBullet(){
        Bullet bullet = game.getEnemy().attack();
        if (bullet != null) {
            bullet.setX(game.getEnemy().getX());
            bullet.setY(game.getEnemy().getY());
            game.getBulletList().add(bullet);
            root.getChildren().add(bullet.getCircle());
        }

    }

    private void playTimelines(){
        coinTimeline.playFromStart();
        heroTimeline.playFromStart();
        animation.playFromStart();
        backgroundTimeline.playFromStart();
        collisionTimeline.playFromStart();
    }

    private void stopTimelines(){
        coinTimeline.stop();
        heroTimeline.stop();
        animation.stop();
        backgroundTimeline.stop();
        collisionTimeline.stop();
    }



    private void initializeTimeline(BorderPane root) {
        // Timeline pour les pièces
        coinTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> addCoin(root))
        );
        coinTimeline.setCycleCount(Timeline.INDEFINITE); // Répétition infinie

        // Timeline pour les héros
        heroTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> addHero(root))
        );
        heroTimeline.setCycleCount(Timeline.INDEFINITE); // Répétition infinie

        //TranslateTransition
        animation = new Timeline( new KeyFrame(Duration.millis(100), event -> {
            game.getCoinsList().forEach(coins -> {
                coins.setX(coins.getX() - 12);
                coins.getImageView().setTranslateX(coins.getX() - WIDTH);
            }) ;
            game.getHerosList().forEach(hero -> {
                hero.setX(hero.getX() - 12);
                hero.getImageView().setTranslateX(hero.getX() - WIDTH);
            }) ;

            game.getBulletList().forEach(bullet -> {
               bullet.setX(bullet.getX() + bullet.getSpeed());
                //bullet.getCircle().setTranslateX(bullet.getX() - WIDTH);
               // bullet.move();  // Utilisez la fonction de déplacement de Bullet
                bullet.getCircle().setTranslateX(bullet.getX());

            });

        }));
        animation.setCycleCount(Timeline.INDEFINITE); // Répétition infinie
        //timeline pour background
        // Création des fonds d'écran
        Image backgroundImage = new Image("file:assets/bg.png");
        ImageView backgroundView1 = new ImageView(backgroundImage);
        ImageView backgroundView2 = new ImageView(backgroundImage);
        // Positionnement initial des fonds d'écran
        backgroundView2.setLayoutX(WIDTH);
        // Ajout des fonds d'écran à la scène
        root.getChildren().addAll(backgroundView1, backgroundView2);
        // Animation des fonds d'écran
        backgroundTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            // Déplacement des fonds d'écran vers la gauche
            backgroundView1.setLayoutX(backgroundView1.getLayoutX() - 5);
            backgroundView2.setLayoutX(backgroundView2.getLayoutX() - 5);
            // Si le premier fond d'écran est complètement sorti de l'écran
            if (backgroundView1.getLayoutX() <= -WIDTH) {
                backgroundView1.setLayoutX(backgroundView2.getLayoutX() + WIDTH);
            }
            // Si le deuxième fond d'écran est complètement sorti de l'écran
            if (backgroundView2.getLayoutX() <= -WIDTH) {
                backgroundView2.setLayoutX(backgroundView1.getLayoutX() + WIDTH);
            }
        }));
        backgroundTimeline.setCycleCount(Timeline.INDEFINITE);

        //Timeline our detection de collision
        collisionTimeline = new Timeline(
                new KeyFrame(Duration.millis(100), event ->{
                    Controller.checkCollisions(this, game);
                }
                        )
        );
        collisionTimeline.setCycleCount(Timeline.INDEFINITE);



    }

    public void removeCoin(Coins coin) {

        root.getChildren().remove(coin.getImageView());
    }

    public void removeHero(Hero hero) {

        root.getChildren().remove(hero.getImageView());
    }

    public void removeBullet(Bullet bullet) {

        root.getChildren().remove(bullet.getCircle());
    }
    //incrementer le nombre de piece dans la scene
    public void updateScore(int value){
        game.setTotalPieces(value);
        piece.setText("Pièces: " + value);

    }

    public void updateLife(int value){
       life.setText("Life :" + value);
       if(value == 0){
           stopTimelines();

           //rajouter mon game over ici !!!
           Image gamaOver = new Image("file:assets/gameOver.png");
           ImageView gameOverView = new ImageView(gamaOver);
           gameOverView.setFitWidth(300);
           gameOverView.setFitHeight(300);
           gameOverView.setX(WIDTH / 2 - 165 );
           gameOverView.setY(80);
           root.getChildren().add(gameOverView);
       }

    }

    // Mettre a jour les points de vies et les pieces
    //essayer de gerer les attaques des ennemi
    @Override
    public void start (Stage primaryStage) {
        // Creation du borderPane
        root = new BorderPane();
        game = new Game();

        // Controller controleur = new Controller( new Model(), this );
        // Création du fond d'écran
        Image background = new Image("file:assets/bg.png");
        ImageView backgroundView = new ImageView(background);
        root.setCenter(backgroundView); // Ajouter le fond d'écran au centre du BorderPane
        game.setEnemy(new Enemy(WIDTH / 2 - 50, 320));



        // creation de la barre de menu
        HBox menu = new HBox();
        menu.setPrefSize(WIDTH, 40);
        menu.setStyle("-fx-background-color: WHITE; -fx-padding: 10px;");
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);

        //Ajout du texte du menu
        //Button resume = new Button("Resume");
        resume = addButton(menu,"Resume");
        life = new Text("Life: 100");
        piece = new Text("Pièce: 0");
        //menu.getChildren().add( resume );
        menu.getChildren().addAll(life, piece);
        root.setBottom(menu); // Ajouter le menu en bas du BorderPane
        // creation de la scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        initializeTimeline(root);
        playTimelines();
        root.getChildren().add(game.getEnemy().getView());

        //gerer le jump
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Faites sauter l'ennemi
                game.jumpEnemy();
            }
        });

        //Gerer l'attaque
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.E) {
                addBullet();
            }
        });


        // AFFICHAGE
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flappy Enemy");
        primaryStage.show();

    //jai une pause a chaque fois que japuie sur E
    //Ma balle ne va pas de la postion de lennemi mais de la gauche de lecran


    }
}
