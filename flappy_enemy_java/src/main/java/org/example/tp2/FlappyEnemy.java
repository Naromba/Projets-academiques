package org.example.tp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class FlappyEnemy extends Application {
    private Enemy enemy;
    private ImageView enemyView;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("View.java") );
        Parent root = loader.load();




        Scene scene = new Scene( root );

        stage.setTitle( " FLAPPY ENNEMY" );
        stage.setScene( scene );
        stage.show();
    }

    //doit creer une autre classe pour le  modele ???
    public static void main(String[] args) {
        launch();
    }
}