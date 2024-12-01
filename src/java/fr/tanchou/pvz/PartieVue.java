package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.board.Row;
import fr.tanchou.pvz.game.board.RowVue;
import fr.tanchou.pvz.player.Player;
import fr.tanchou.pvz.player.gui.HudPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class PartieVue extends Application {
    private Pane rootPane;
    private Pane animationLayer;
    private HudPane hudLayer;
    private Pane sunLayer;

    private PartieController controller;

    private int mouseX;
    private int mouseY;

    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        rootPane = new Pane();

        animationLayer = new Pane();
        sunLayer = new Pane();

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/assets/terrains/day.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Positionne l'image dans le coin supérieur gauche
        backgroundImageView.setX(0);
        backgroundImageView.setY(0);

        // Ajoute l'image de fond en premier dans le rootPane
        rootPane.getChildren().add(backgroundImageView);

        // Création du modèle et du joueur
        Player player = new Player("Louis");
        Partie partie = new Partie(player, rootPane);

        hudLayer = new HudPane(player, animationLayer);
        rootPane.getChildren().addAll(animationLayer, sunLayer, hudLayer);
        // Création du contrôleur avec le modèle et la vue
        controller = new PartieController(partie, this);

        // Démarrage du jeu
        controller.startGame();
        this.scene = new Scene(rootPane, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Plante versus Zombie - " + player.getName());

        backgroundImageView.setFitWidth(rootPane.getWidth()+0.35*rootPane.getWidth());
        backgroundImageView.setFitHeight(rootPane.getHeight());

        animationLayer.setLayoutX(rootPane.getWidth()*0.25); // Décalage horizontal
        animationLayer.setLayoutY(rootPane.getHeight()*0.1); // Décalage vertical

        rootPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        animationLayer.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
        hudLayer.setStyle("-fx-border-color: green; -fx-border-width: 2;");

        scene.setOnMouseMoved(mouseEvent -> {
            mouseX = (int) mouseEvent.getX();
            mouseY = (int) mouseEvent.getY();
        });

        /*animationLayer.setOnMouseClicked(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            if (player.getSelectedPlant() != null) {
                partie.placePlantUnderMouse(mouseX, mouseY);
            }
            System.out.println("Mouse clicked at " + mouseX + ", " + mouseY + " ans player selected plant is " + player.getSelectedPlant());
        });*/

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

            RowVue rowVue = row.getRowVue();
            Pane rowPane = rowVue.getRowPane();

            // Si le rowPane n'est pas déjà ajouté, ajoute-le
            if (!animationLayer.getChildren().contains(rowPane)) {
                rowVue.setSize(rootPane.getWidth()*0.75, rootPane.getHeight()*0.17);  // Taille du Pane parent
                rowPane.setLayoutY(i * (rootPane.getHeight()*0.17));  // Par exemple, espacer les lignes verticalement
                animationLayer.getChildren().add(rowPane);
            }

            // Mettre à jour l'affichage des entités dans la ligne
            rowVue.update();
            i++;
        }


        // Mettre à jour l'affichage des informations du joueur
        hudLayer.update(mouseX, mouseY);

        // Mettre à jour les soleils
        partie.getSunManager().updateSunsView(sunLayer);
    }
}
