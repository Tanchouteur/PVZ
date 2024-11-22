package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.Row;
import fr.tanchou.pvz.game.RowVue;
import fr.tanchou.pvz.player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PartieVue extends Application {
    private Pane rootPane;
    private Pane animationLayer;
    private Pane hudLayer;

    private PartieController controller;


    @Override
    public void start(Stage primaryStage) {
        rootPane = new Pane();

        animationLayer = new Pane();
        animationLayer.setLayoutX(100); // Décalage horizontal
        animationLayer.setLayoutY(50); // Décalage vertical


        hudLayer = new Pane();

        Image backgroundImage = new Image(String.valueOf(getClass().getResource( "/assets/terrains/day.webp")));

        // Crée un ImageView pour afficher l'image de fond
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Assure-toi que l'image couvre toute la fenêtre
        backgroundImageView.setFitWidth(1090);  // Largeur de ton jeu
        backgroundImageView.setFitHeight(600);  // Hauteur de ton jeu

        // Positionne l'image dans le coin supérieur gauche
        backgroundImageView.setX(0);
        backgroundImageView.setY(0);

        // Ajoute l'image de fond en premier dans le rootPane
        rootPane.getChildren().add(backgroundImageView);

        rootPane.getChildren().addAll(animationLayer, hudLayer);

        // Création du modèle et du joueur
        Player player = new Player("Louis");
        Partie partie = new Partie(player);

        // Création du contrôleur avec le modèle et la vue
        controller = new PartieController(partie, this);

        // Démarrage du jeu
        controller.startGame();

        primaryStage.setScene(new Scene(rootPane, 1200, 800));
        primaryStage.setTitle("Plante versus Zombie");

        primaryStage.show();
    }

    public Pane getAnimationLayer() {
        return animationLayer;
    }

    // Mise à jour de la vue avec les informations du modèle
    public void update(Partie partie) {

        if (partie.isDefeated()) {
            controller.stopGame();
            return;
        }

        int i = 0;

        // Mettre à jour chaque ligne (Row) dans le jeu
        for (Row row : partie.getRows()) {
            i++;
            RowVue rowVue = row.getRowVue();
            Pane rowPane = rowVue.getRowPane();

            // Si le rowPane n'est pas déjà ajouté, ajoute-le
            if (!animationLayer.getChildren().contains(rowPane)) {
                rowPane.setLayoutY(i * 100);  // Par exemple, espacer les lignes verticalement
                animationLayer.getChildren().add(rowPane);
            }

            // Mettre à jour l'affichage des entités dans la ligne
            rowVue.update();
        }
    }
}
