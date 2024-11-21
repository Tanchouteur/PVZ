package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.Row;
import fr.tanchou.pvz.game.RowVue;
import fr.tanchou.pvz.player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PartieVue extends Application {
    private Pane rootPane;
    private Pane animationLayer;
    private PartieController controller;


    @Override
    public void start(Stage primaryStage) {
        rootPane = new Pane();
        animationLayer = new Pane();

        rootPane.getChildren().addAll(animationLayer);

        // Création du modèle et du joueur
        Player player = new Player("Louis");
        Partie partie = new Partie(player);

        // Création du contrôleur avec le modèle et la vue
        controller = new PartieController(partie, this);

        // Démarrage du jeu
        controller.startGame();

        primaryStage.setScene(new Scene(rootPane, 800, 600));
        primaryStage.setTitle("Plante versus Zombie");

        primaryStage.show();
    }

    public Pane getAnimationLayer() {
        return animationLayer;
    }

    // Mise à jour de la vue avec les informations du modèle
    public void update(Partie partie) {
        int i = 0;

        // Mettre à jour chaque ligne (Row) dans le jeu
        for (Row row : partie.getRows()) {
            i++;
            RowVue rowVue = row.getRowVue();
            Pane rowPane = rowVue.getRowPane();

            // Si le rowPane n'est pas déjà ajouté au rootPane, ajoute-le
            if (!rootPane.getChildren().contains(rowPane)) {
                rowPane.setLayoutY(i * 100);  // Par exemple, espacer les lignes verticalement
                rootPane.getChildren().add(rowPane);
            }

            // Mettre à jour l'affichage des entités dans la ligne
            rowVue.update();
        }
    }
}
