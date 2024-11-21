package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PartieVue extends Application {
    private Pane rootPane;
    private GridPane gridPane;
    private Pane animationLayer;
    private PartieController controller;

    @Override
    public void start(Stage primaryStage) {
        rootPane = new Pane();
        gridPane = new GridPane();
        animationLayer = new Pane();

        rootPane.getChildren().addAll(gridPane, animationLayer);

        Player player = new Player("Louis"); // Création du joueur
        Partie partie = new Partie(player); // Création du modèle

        controller = new PartieController(partie, this); // Lien avec le contrôleur
        primaryStage.setScene(new Scene(rootPane, 800, 600));
        primaryStage.show();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Pane getAnimationLayer() {
        return animationLayer;
    }

    public void update(Partie partie) {

    }
}
