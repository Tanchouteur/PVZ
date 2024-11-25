package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.entities.plants.PlantCard;
import fr.tanchou.pvz.entities.plants.VuePlantCard;
import fr.tanchou.pvz.player.Player;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HudPane extends Pane {

    private Player player;
    private Pane plantPane;

    private ControllerPlantCard controllerPlantCard;

    public HudPane(Player player, Pane animationLayer) {
        this.setPrefSize(200, 600);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Ajout des éléments graphiques
        this.player = player;
        this.plantPane = createPlantPane();
        this.controllerPlantCard = new ControllerPlantCard(animationLayer);
        this.getChildren().add(plantPane);
    }

    private Pane createPlantPane() {
        VBox plantPane = new VBox(); // VBox pour un empilement vertical
        plantPane.setSpacing(5); // Espacement vertical entre les cartes
        plantPane.setLayoutX(0);
        plantPane.setLayoutY(0);
        plantPane.setPrefSize(200, 500);
        plantPane.setStyle("-fx-background-color: rgba(0,0,0,0);");

        // Ajout des éléments graphiques
        for (PlantCard plantCard : player.getPlantCards()) {
            if (plantCard != null) {
                VuePlantCard vuePlantCard = plantCard.createVue();
                vuePlantCard.setOnMouseClicked(controllerPlantCard);
                plantPane.getChildren().add(vuePlantCard); // Ajout à VBox
            }
        }

        return plantPane;
    }


    public void update() {
        // Mise à jour des éléments graphiques
    }


}
