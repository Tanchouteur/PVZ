package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.PlantCard;
import fr.tanchou.pvz.entities.plants.VuePlantCard;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControllerPlantCard implements EventHandler<MouseEvent> {

    private Pane gamePane; // Le Pane où les plantes sont placées (ex : le terrain de jeu)

    public ControllerPlantCard(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public VuePlantCard createVue(PlantCard plantCard) {
        VuePlantCard vuePlantCard = new VuePlantCard(plantCard);

        vuePlantCard.setFitHeight(100);
        vuePlantCard.setFitWidth(100);
        return vuePlantCard;
    }

    public void createPlantUnderMouse(Plant plant, double mouseX, double mouseY) {
        // Créer un objet représentant la plante et la placer à la position de la souris
        plant.getVue().getImageView().setLayoutX(mouseX - plant.getVue().getImageView().getFitWidth() / 2); // Centrer la plante sur la souris
        plant.getVue().getImageView().setLayoutY(mouseY - plant.getVue().getImageView().getFitHeight() / 2);

        // Ajoutez la vuePlant à un conteneur (ex: Pane de jeu)
        gamePane.getChildren().add(plant.getVue().getImageView());
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof VuePlantCard vuePlantCard)) {
            return;
        }
        PlantCard plantCard = vuePlantCard.getPlantCard();

        System.out.println("Clicked on " + plantCard.getName());

        // Créer une nouvelle plante et la placer sous la souris
        Plant plant = plantCard.getPlant(); // Crée une nouvelle plante avec la carte actuelle
        createPlantUnderMouse(plant, mouseEvent.getSceneX(), mouseEvent.getSceneY()); // Place la plante sous la souris
    }
}
