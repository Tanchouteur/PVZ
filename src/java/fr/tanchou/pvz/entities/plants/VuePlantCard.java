package fr.tanchou.pvz.entities.plants;

import javax.swing.text.html.ImageView;
import java.net.URL;

public class VuePlantCard  {
    private final PlantCard plantCard;

    public VuePlantCard(PlantCard plantCard) {
        //super(new ImageView(plantCard.getURL()).toTe);
        this.plantCard = plantCard;

    }

    public PlantCard getPlantCard() {
        return plantCard;
    }
}
