package fr.tanchou.pvz.entities.plants;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class VuePlantCard extends ImageView {
    private final PlantCard plantCard;

    public VuePlantCard(PlantCard plantCard) {
        super(new Image(plantCard.getURL().toExternalForm())); // Convertit URL en String
        this.plantCard = plantCard;
    }

    public PlantCard getPlantCard() {
        return plantCard;
    }
}

