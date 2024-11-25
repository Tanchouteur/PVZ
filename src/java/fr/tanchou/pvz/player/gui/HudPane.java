package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.entities.plants.Plant;
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
                vuePlantCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (!(mouseEvent.getSource() instanceof VuePlantCard vuePlantCard)) {
                            return;
                        }
                        PlantCard plantCard = vuePlantCard.getPlantCard();

                        System.out.println("Clicked on " + plantCard.getName());

                        // Créer une nouvelle plante et la placer sous la souris
                        Plant plant = plantCard.getPlant(); // Crée une nouvelle plante avec la carte actuelle
                        createPlantUnderMouse(plant, mouseEvent.getSceneX(), mouseEvent.getSceneY());
                    }
                });
                plantPane.getChildren().add(vuePlantCard); // Ajout à VBox
            }
        }

        return plantPane;
    }


    public void update() {
        // Mise à jour des éléments graphiques
    }

    public void createPlantUnderMouse(Plant plant, double mouseX, double mouseY) {
        // Créer un objet représentant la plante et la placer à la position de la souris
        plant.setVue(plant.createVue(plantPane));
        plant.getVue().getImageView().setLayoutX(mouseX - plant.getVue().getImageView().getFitWidth() / 2); // Centrer la plante sur la souris
        plant.getVue().getImageView().setLayoutY(mouseY - plant.getVue().getImageView().getFitHeight() / 2);

        // Ajoutez la vuePlant à un conteneur (ex: Pane de jeu)
        plantPane.getChildren().add(plant.getVue().getImageView());
    }

}
